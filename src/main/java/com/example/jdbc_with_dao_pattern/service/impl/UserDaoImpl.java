package com.example.jdbc_with_dao_pattern.service.impl;

import com.example.jdbc_with_dao_pattern.service.UserDao;
import com.example.jdbc_with_dao_pattern.dto.response.UserCreateResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UserUpdateResponse;
import com.example.jdbc_with_dao_pattern.exception.BadRequestException;
import com.example.jdbc_with_dao_pattern.exception.ConflictException;
import com.example.jdbc_with_dao_pattern.exception.NotFoundException;
import com.example.jdbc_with_dao_pattern.model.User;
import com.example.jdbc_with_dao_pattern.validation.UserValidation;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.QueryOfMysql.*;

@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final HikariDataSource dataSource;
    @Autowired
    private UserValidation validation;

    //region DAO
    @Override
    public User save(User user) throws SQLException {
        String sql = String.format(CREATE_USER, user.getId(), user.getUserName(), user.getPassword(), user.getPrice());
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false); // start transaction
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.commit();//commit transasction
        } catch (SQLException e) {
            log.error("(save) save user fail, cause {}", e.toString());
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
        Connection connection = dataSource.getConnection();
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
        Connection connection = dataSource.getConnection();
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
        Connection connection = dataSource.getConnection();
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
        Connection connection = dataSource.getConnection();
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
    public UserCreateResponse create(String userName, String password, Long price) throws SQLException {
        User user = new User();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            validation.validUserNameExist(userName);
            String id = UUID.randomUUID().toString();
            price = Objects.isNull(price) ? 0L : price;
            user = User.of(id, userName, password, price);
            //code ky phan nay vao
            UserCreateResponse.from(save(user));
            log.info("(create) create {} successfully", userName);
        } catch (SQLException e) {
            log.info("(create) create {} fail", userName);
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        return UserCreateResponse.from(save(user));
    }

    @Override
    public UserUpdateResponse update(String id, String newPassword, Long newPrice) throws SQLException {
        User user = new User();
        UserUpdateResponse userUpdateResponse = new UserUpdateResponse();
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        try {
            validation.validNotFoundById(id);
            user = get(id);
            String password = Objects.isNull(newPassword) || newPassword.isEmpty() ? user.getPassword() : newPassword;
            Long price = Objects.isNull(newPrice) || newPrice.toString().isEmpty() ? user.getPrice() : newPrice;
            user = User.of(id, user.getUserName(), password, price);
            userUpdateResponse = UserUpdateResponse.from(update(user));
        } catch (SQLException e) {
            log.info("(update) update user has id {} fail", id);
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // return auto commit to true
                connection.close();
            }
        }
        log.info("(update) update {}", id);
        return userUpdateResponse;
    }

    @Override
    public void bank(String fromUser, String toUser, Long price) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            // tru tien nguoi chuyen
            validation.validNotFoundByUserName(fromUser);
            String sql = String.format(DECREASE_PRICE_OF_SEND_USER, price, fromUser);
            User user = getByUsername(fromUser);
            if (price < user.getPrice()) throw new BadRequestException();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(sql);

            // tang tien cho nguoi nhan
            validation.validNotFoundByUserName(toUser);
            sql = String.format(INCREASE_PRICE_OF_RECEIVED_USER, price, toUser);
            statement.execute(sql);
            log.info("(bank) {} bank to {} {} successfully", fromUser, toUser, price);
            connection.commit();
        } catch (SQLException e) {
            log.info("(bank) {} bank to {} {} failed", fromUser, toUser, price);
            connection.rollback();
        }
    }

    public User getByUsername(String username) throws SQLException {
        User user = new User();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format(GET_USER_BY_USERNAME, user);
            System.out.println(sql);
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

//    private void validUserNameExist(String userName) throws SQLException {
//        Connection connection = dataSource.getConnection();
//        String sql = String.format(EXIST_BY_USER_NAME, userName);
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        if (resultSet.next()) throw new ConflictException();
//        log.info("(validUserNameExist) validUserNameExist by username: {}", userName);
//    }
//
//    private void validNotFoundById(String id) throws SQLException {
//        Connection connection = dataSource.getConnection();
//        String sql = String.format(FIND_BY_ID, id);
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        if (!resultSet.next()) throw new NotFoundException();
//        log.info("(validNotFoundById) not found by id: {}", id);
//    }
//
//    private void validNotFoundByUserName(String username) throws SQLException {
//        Connection connection = dataSource.getConnection();
//        String sql = String.format(FIND_BY_USERNAME, username);
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        if (!resultSet.next()) throw new NotFoundException();
//        log.info("(validNotFoundByUserName) not found by username: {}", username);
//    }
}
