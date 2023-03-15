package com.example.jdbcwithdaop.dao.impl;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.jdbcwithdaop.constant.JdbcWithDaoPatternConstant.QueryOfMysql.*;

@Slf4j
public class UserDaoImpl implements UserDao {

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User get(String id) throws SQLException {
        User user = new User();
        Statement statement = connection.createStatement();
        String sql = GET_USER_BY_ID + id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setPrice(rs.getBigDecimal("price"));
        }
        connection.close();
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> resultUser = new ArrayList<>();
        try{

            Statement statement = connection.createStatement();
            String sql = GET_ALL_USER;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setPrice(rs.getBigDecimal("price"));
            }
        }catch (SQLException e){
            connection.rollback();
        }
        return resultUser;
    }

    @Override
    public User save(User user) throws SQLException {
        connection.setAutoCommit(false);
        User resultUser = new User();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(CREATE_USER, user.getId(), user.getUserName(), user.getPassword(), user.getPrice());
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }

        try {
            Statement statement = connection.createStatement();
            String sql = GET_USER_BY_ID + user.getId();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setPrice(rs.getBigDecimal("price"));
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        return resultUser;
    }

    @Override
    public User update(User user) throws SQLException {
        connection.setAutoCommit(false);
        User resultUser = new User();

        try {
            Statement statement = connection.createStatement();
            String sql = String.format(UPDATE_USER_BY_ID, user.getUserName(), user.getPassword(), user.getPrice(), user.getId());
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        try {
            Statement statement = connection.createStatement();
            String sql = GET_USER_BY_ID + user.getId();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setPrice(rs.getBigDecimal("price"));
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        return resultUser;
    }

    @Override
    public void delete(String id) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = DELETE_USER_BY_ID + id;
            log.info("(delete) delte user has id {} successfully",id);
        }catch (SQLException e){
            log.error("(delete) delte user has id {} failed",id);
            connection.rollback();
        }
    }

    public String testConnection() {

        System.out.println("Get connection " + connection.toString());

        System.out.println("Done!");
        return "thanh cong";
    }
    @Override
    public void bank(String fromUser, String toUser, BigDecimal price) throws SQLException {
        try {
            // tru tien nguoi chuyen
            String sql = String.format(DECREASE_PRICE_OF_SEND_USER,price,fromUser);
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(sql);
            // tang tien cho nguoi nhan
            sql = String.format(INCREASE_PRICE_OF_RECEIVED_USER,price,toUser);
            statement.execute(sql);
            log.info("(bank) {} bank to {} {} successfully", fromUser, fromUser, price);
            connection.commit();
        } catch (SQLException e) {
            log.info("(bank) {} bank to {} {} failed", fromUser, fromUser, price);
            connection.rollback();
        }
    }
/*
    Using jdbc template
        @Override
    public User get(String id) {
        String sql = "SELECT * FROM users WHERE id=" + id;
        return connection.(sql, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setPrice(rs.getBigDecimal("price"));
                    return user;
                }
                return null;
            }
        });
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();

                user.setId(rs.getString("id"));
                user.setUser_name(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setPrice(rs.getBigDecimal("price"));

                return user;
            }
        });

        return users;
    }

    @Override
    public User save(User user) {
        BigDecimal price = user.getPrice() == null ? BigDecimal.valueOf(0) : user.getPrice();
        user.setPrice(BigDecimal.valueOf(1000));
        String sql = "INSERT INTO users (id, user_name, password, price)"
                + " VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getUser_name(), user.getPassword(), user.getPrice());
        log.info("save user successfully");

        sql = "SELECT * FROM users WHERE id=" + user.getId();
        return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setPrice(rs.getBigDecimal("price"));
                    return user;
                }
                return null;
            }
        });
    }


    @Override
    public User update(User user) {
        String sql = "UPDATE users SET id=?, user_name=?, password=?, "
                + "price=? WHERE id=?";

        jdbcTemplate.update(sql, user.getId(), user.getUser_name(),
                user.getPassword(), user.getPrice(), user.getId());
        log.info("update user successfully");

        sql = "SELECT * FROM users WHERE id=" + user.getId();
        return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setPrice(rs.getBigDecimal("price"));
                    return user;
                }
                return null;
            }
        });
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM users WHERE users.id=?";
        jdbcTemplate.update(sql, id);
        log.info("delete user successfully");
    }
*/
}
