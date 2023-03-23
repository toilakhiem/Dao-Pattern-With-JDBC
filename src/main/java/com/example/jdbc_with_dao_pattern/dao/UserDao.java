package com.example.jdbc_with_dao_pattern.dao;

import com.example.jdbc_with_dao_pattern.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User save(User user) throws SQLException;
    User get(String id) throws SQLException;
    List<User> getAll() throws SQLException;
    User update(User user) throws SQLException;
    void delete(String id) throws SQLException;
    boolean existByUserName(String username) throws SQLException;
    boolean existById(String id) throws SQLException;
}
