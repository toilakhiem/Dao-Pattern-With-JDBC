package com.example.jdbc_with_dao_pattern.validation;

import com.example.jdbc_with_dao_pattern.exception.ConflictException;
import com.example.jdbc_with_dao_pattern.exception.NotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.QueryOfMysql.*;

@Slf4j
@RequiredArgsConstructor
public class UserValidation {
    private final HikariDataSource dataSource;
    public void validUserNameExist(String userName) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = String.format(EXIST_BY_USER_NAME, userName);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                log.info("(validUserNameExist) validUserNameExist by username: {}", userName);
                throw new ConflictException();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }

    }

    public void validNotFoundById(String id) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = String.format(FIND_BY_ID, id);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                log.info("(validNotFoundById) not found by id: {}", id);
                throw new ConflictException();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
    }

    public void validNotFoundByUserName(String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = String.format(FIND_BY_USERNAME, username);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                log.info("(validNotFoundByUserName) not found by username: {}", username);
                throw new NotFoundException();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
    }
}
