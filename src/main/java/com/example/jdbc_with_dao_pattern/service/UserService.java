package com.example.jdbc_with_dao_pattern.service;

import com.example.jdbc_with_dao_pattern.dto.response.CreateUserResponse;
import com.example.jdbc_with_dao_pattern.dto.response.GetAllUserResponse;
import com.example.jdbc_with_dao_pattern.dto.response.GetUserResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UpdateUserResponse;

import java.sql.SQLException;

public interface UserService {
    CreateUserResponse create(String userName, String password, Long price) throws SQLException;
    UpdateUserResponse update(String id, String password, Long price) throws SQLException;
    GetUserResponse get(String id) throws SQLException;
    GetAllUserResponse getAll() throws SQLException;
    void delete(String id) throws SQLException;
}
