package com.example.jdbc_with_dao_pattern.dao.impl;

import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.dto.response.UserCreateResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UserUpdateResponse;
import com.example.jdbc_with_dao_pattern.exception.BadRequestException;
import com.example.jdbc_with_dao_pattern.exception.ConflictException;
import com.example.jdbc_with_dao_pattern.exception.NotFoundException;
import com.example.jdbc_with_dao_pattern.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.QueryOfMysql.*;

@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final Connection connection;

    //region DAO
    @Override
    public User get(String id) throws SQLException {
        User user = new User();
        Statement statement = connection.createStatement();
        String sql = String.format(GET_USER_BY_ID, id);
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setPrice(rs.getLong("price"));
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> resultUser = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = GET_ALL_USER;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setPrice(rs.getLong("price"));
            resultUser.add(user);
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
        resultUser = get(user.getId());
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
            log.info("(update) update user has id {} successfully", user.getId());
        } catch (SQLException e) {
            connection.rollback();
            log.error("(update) update user has id {} failed", user.getId());
        }
        resultUser = get(user.getId());
        return resultUser;
    }

    @Override
    public void delete(String id) throws SQLException {
        connection.setAutoCommit(false);
        try {
            Statement statement = connection.createStatement();
            String sql = DELETE_USER_BY_ID + id;
            log.info("(delete) delete user has id {} successfully", id);
        } catch (SQLException e) {
            log.error("(delete) delete user has id {} failed", id);
            connection.rollback();
        }
    }

    //endregion

    @Override
    public UserCreateResponse create(String userName, String password, Long price) throws SQLException {
        User user = new User();
        try {
            connection.setAutoCommit(false);
            validUserNameExist(userName);
            String id = UUID.randomUUID().toString();
            price = Objects.isNull(price) ? 0L : price;
            user = User.of(id, userName, password, price);
            log.info("(create) create {} successfully", userName);
        } catch (SQLException e) {
            log.info("(create) create {} fail", userName);
            connection.rollback();
        }
        return UserCreateResponse.from(save(user));
    }

    @Override
    public UserUpdateResponse update(String id, String newPassword, Long newPrice) throws SQLException {
        User user = new User();
        try {
            connection.setAutoCommit(false);
            validNotFoundById(id);
            user = get(id);
        } catch (SQLException e) {
            log.info("(update) update user has id {} fail", id);
            connection.rollback();
        }
        log.info("(update) update {}", id);
        String password = Objects.isNull(newPassword) || newPassword.isEmpty() ? user.getPassword() : newPassword;
        Long price = Objects.isNull(newPrice) || newPrice.toString().isEmpty() ? user.getPrice() : newPrice;
        user = User.of(id, user.getUserName(), password, price);
        return UserUpdateResponse.from(update(user));
    }

    @Override
    public void bank(String fromUser, String toUser, Long price) throws SQLException {
        try {
            // tru tien nguoi chuyen
            validNotFoundByUserName(fromUser);
            String sql = String.format(DECREASE_PRICE_OF_SEND_USER, price, fromUser);
            User user = getByUsername(fromUser);
            if(price < user.getPrice()) throw new BadRequestException();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(sql);

            // tang tien cho nguoi nhan
            validNotFoundByUserName(toUser);
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
        return user;
    }
    private void validUserNameExist(String userName) throws SQLException {
        String sql = String.format(EXIST_BY_USER_NAME, userName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) throw new ConflictException();
        log.info("(validUserNameExist) validUserNameExist by username: {}", userName);
    }

    private void validNotFoundById(String id) throws SQLException {
        String sql = String.format(FIND_BY_ID, id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) throw new NotFoundException();
        log.info("(validNotFoundById) not found by id: {}", id);
    }
    private void validNotFoundByUserName(String username) throws SQLException {
        String sql = String.format(FIND_BY_USERNAME, username);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) throw new NotFoundException();
        log.info("(validNotFoundByUserName) not found by username: {}", username);
    }
}
