package com.example.jdbc_with_dao_pattern.dao;

import com.example.jdbc_with_dao_pattern.dto.response.UserCreateResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UserUpdateResponse;
import com.example.jdbc_with_dao_pattern.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User get(String id) throws SQLException;

    List<User> getAll() throws SQLException;

    User save(User user) throws SQLException;

    User update(User user) throws SQLException;

    void delete(String id) throws SQLException;

    void bank(String fromUser, String toUser, Long price) throws SQLException;

    UserCreateResponse create(String userName, String password, Long price) throws SQLException;
    UserUpdateResponse update(String id, String password, Long price ) throws SQLException;
}
