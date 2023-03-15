package com.example.jdbcwithdaop.dao;

import com.example.jdbcwithdaop.dao.base.Dao;
import com.example.jdbcwithdaop.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User get(String id) throws SQLException;
    List<User> getAll() throws SQLException;
    User save(User user) throws SQLException;
    User update(User user) throws SQLException;
    void delete(String id) throws SQLException;
    void bank(String fromUser, String toUser, BigDecimal price) throws SQLException;
    String testConnection();
}
