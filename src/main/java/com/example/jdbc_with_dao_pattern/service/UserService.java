package com.example.jdbc_with_dao_pattern.service;

import com.example.jdbc_with_dao_pattern.dto.response.UserCreateResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UserUpdateResponse;

import java.sql.SQLException;

public interface UserService {

    UserCreateResponse create(String userName, String password, Long price) throws SQLException;

    UserUpdateResponse update(String id, String password, Long price) throws SQLException;

    void bank(String fromUser, String toUser, Long price) throws SQLException;
}
