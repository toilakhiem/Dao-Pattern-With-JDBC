package com.example.jdbcwithdaop.dao.impl;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dao.base.Dao;
import com.example.jdbcwithdaop.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User get(String id) {
        String sql = "SELECT * FROM users WHERE id=" + id;
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
                + "price=? WHERE contact_id=?";

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

    @Override
    public void bank(String fromUsername, String toUsername, BigDecimal price) {
        // tru tien nguoi chuyen
        String sql = "UPDATE users SET price = price - ? where user_name = ?";
        jdbcTemplate.update(sql,price,fromUsername);

        // tang tien cho nguoi nhan
        sql = "UPDATE users SET price = price + ? where user_name = ?";
        jdbcTemplate.update(sql,price,toUsername);

        log.info("{} bank to {} {} ", fromUsername, toUsername, price);
    }
}
