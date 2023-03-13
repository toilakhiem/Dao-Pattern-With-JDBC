package com.example.jdbcwithdaop.Configration.dao.impl;

import com.example.jdbcwithdaop.Configration.dao.base.Dao;
import com.example.jdbcwithdaop.Configration.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
public class UserDaoImpl2 implements Dao<User> {
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl2(DataSource dataSource) {
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
                    user.setPrice(rs.getDouble("price"));
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
                user.setPrice(rs.getDouble("price"));

                return user;
            }

        });

        return users;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (id, user_name, password, price)"
                + " VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getUser_name(), user.getPassword(), user.getPrice());
        log.info("save user successfully");
    }

    @Override
    public void updat(User user) {
        String sql = "UPDATE contact SET id=?, user_name=?, password=?, "
                + "price=? WHERE contact_id=?";

        jdbcTemplate.update(sql, user.getId(), user.getUser_name(),
                user.getPassword(), user.getPrice(), user.getId());
        log.info("update user successfully");
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM users WHERE users.id=?";
        jdbcTemplate.update(sql, id);
        log.info("delete user successfully");
    }
}
