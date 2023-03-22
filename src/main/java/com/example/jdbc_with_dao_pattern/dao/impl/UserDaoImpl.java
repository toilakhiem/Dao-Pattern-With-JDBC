package com.example.jdbc_with_dao_pattern.dao.impl;

import com.example.jdbc_with_dao_pattern.dao.HikariCPConfiguration;
import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.exception.ConflictException;
import com.example.jdbc_with_dao_pattern.model.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.QueryOfMysql.*;

@Slf4j
public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) throws SQLException {
        String sql = String.format(CREATE_USER, user.getId(), user.getUserName(), user.getPassword(), user.getPrice());
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false); // start transaction
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.commit();//commit transasction
        } catch (SQLException e) {
            log.error("(save) cause {}", e.toString());
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        return get(user.getId());
    }

    @Override
    public User get(String id) throws SQLException {
        User user = new User();
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(GET_USER_BY_ID, id);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setPrice(rs.getLong("price"));
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> resultUser = new ArrayList<>();
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        String sql = GET_ALL_USER;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setPrice(rs.getLong("price"));
                resultUser.add(user);
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        return resultUser;
    }

    @Override
    public User update(User user) throws SQLException {
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        User resultUser = new User();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(UPDATE_USER_BY_ID, user.getUserName(), user.getPassword(), user.getPrice(), user.getId());
            statement.executeUpdate(sql);
            connection.commit();
            log.info("(update) update user has id {} successfully", user.getId());
        } catch (SQLException e) {
            connection.rollback();
            log.error("(update) update user has id {} failed", user.getId());
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        resultUser = get(user.getId());
        return resultUser;
    }

    @Override
    public void delete(String id) throws SQLException {
        //note : dat log khi bd, doan dc loi, cho xu ly phuc tap
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            Statement statement = connection.createStatement();
            String sql = DELETE_USER_BY_ID + id;
            log.info("(delete) delete user has id {} successfully", id);
        } catch (SQLException e) {
            log.error("(delete) delete user has id {} failed", id);
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
    }

    //endregion
    @Override
    public boolean existByUserName(String username) throws SQLException {
        String sql = String.format(EXIST_BY_USER_NAME, username);
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false); // start transaction
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();//commit transasction
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            log.error("(existByUserName) cause {}", e.toString());
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        return false;
    }

    @Override
    public boolean existById(String id) throws SQLException {
        String sql = String.format(EXIST_BY_ID, id);
        Connection connection = HikariCPConfiguration.getInstance().getConnection();
        connection.setAutoCommit(false); // start transaction
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();//commit transasction
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            log.error("(existByIdy) cause {}", e.toString());
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        return false;
    }
}
