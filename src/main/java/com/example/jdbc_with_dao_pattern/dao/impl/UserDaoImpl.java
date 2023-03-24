package com.example.jdbc_with_dao_pattern.dao.impl;

import com.example.jdbc_with_dao_pattern.dao.HikariCPConfiguration;
import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.model.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.QueryOfMysql.*;

@Slf4j
public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) throws SQLException {
        User resultUSer = null;
        String sql = String.format(CREATE_USER, user.getId(), user.getUserName(), user.getPassword(), user.getPrice());
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        log.info("(save) start");
        try {
            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(sql);
            if (rs == 0) connection.rollback();
            connection.commit();
            log.info("(save) save user has id {} successfully", user.getId());
            resultUSer = get(user.getId());
        } catch (SQLException e) {
            log.error("(save) cause {}", e.toString());
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return resultUSer;
    }

    @Override
    public User get(String id) throws SQLException {
        User user = new User();
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        log.info("(get) start");
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(GET_USER_BY_ID, id);
            ResultSet rs = statement.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
                log.error("(get) not found user has id {}", id);
                throw new SQLException("Not found");
            } else {
                while (rs.next()) {
                    user.setId(rs.getString("id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setPrice(rs.getLong("price"));
                }
            }
            log.info("(get) successfully");
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> resultUser = new ArrayList<>();
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        log.info("(getAll) start");
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE);
            String sql = GET_ALL_USER;
            ResultSet rs = statement.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
                log.error("(get) not found any user");
                throw new SQLException("Not found");
            } else {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setPrice(rs.getLong("price"));
                    resultUser.add(user);
                }
            }
            log.info("(getAll) successfully");
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return resultUser;
    }

    @Override
    public User update(User user) throws SQLException {
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        log.info("(update) start");
        User resultUser;
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(UPDATE_USER_BY_ID, user.getUserName(), user.getPassword(), user.getPrice(), user.getId());
            int rs = statement.executeUpdate(sql);
            if (rs == 0) {
                log.error("(update) fail");
                throw new SQLException();
            }
            connection.commit();
            log.info("(update) update user has id {} successfully", user.getId());
        } catch (SQLException e) {
            connection.rollback();
            log.error("(update) update user has id {} failed", user.getId());
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        resultUser = get(user.getId());
        return resultUser;
    }

    @Override
    public void delete(String id) throws SQLException {
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        log.info("(delete) start", id);
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(DELETE_USER_BY_ID, id);
            int rs = statement.executeUpdate(sql);
            if (rs == 0) {
                log.error("(delete) fail");
                throw new SQLException();
            }
            log.info("(delete) successfully", id);
        } catch (SQLException e) {
            log.error("(delete) delete failed", id);
            connection.rollback();
        } finally {
            if (Objects.nonNull(connection)) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    //endregion
    @Override
    public boolean existByUserName(String username) throws SQLException {
        String sql = String.format(EXIST_BY_USER_NAME, username);
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        log.info("(existByUserName) start");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();
            log.info("(existByUserName) successfully");
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            log.error("(existByUserName) cause {}", e.toString());
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return false;
    }

    @Override
    public boolean existById(String id) throws SQLException {
        String sql = String.format(EXIST_BY_ID, id);
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        log.info("(existByUserName) start");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();
            log.info("(existByUserName) successfully");
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            log.error("(existById) cause {}", e.toString());
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return false;
    }
}
