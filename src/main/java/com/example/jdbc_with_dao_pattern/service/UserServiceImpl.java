package com.example.jdbc_with_dao_pattern.service;

import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.dto.response.UserCreateResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UserUpdateResponse;
import com.example.jdbc_with_dao_pattern.exception.ConflictException;
import com.example.jdbc_with_dao_pattern.exception.NotFoundException;
import com.example.jdbc_with_dao_pattern.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserCreateResponse create(String userName, String password, Long price) throws SQLException {
        String id = UUID.randomUUID().toString();
        validUserNameExisted(userName);
        price = Objects.isNull(price) ? 0L : price;
        User user = User.of(id, userName, password, price);
        return UserCreateResponse.from(userDao.save(user));
    }

    @Override
    public UserUpdateResponse update(String id, String password, Long price) throws SQLException {
        validNotFoundById(id);
        return UserUpdateResponse.from(userDao.update(userDao.get(id)));
    }

    @Override
    public void bank(String fromUser, String toUser, Long price) throws SQLException {

    }

    private void validUserNameExisted(String userName) throws SQLException {
        if (userDao.existByUserName(userName)) throw new ConflictException();
    }
    public void validNotFoundById(String id) throws SQLException {
       if(!userDao.existById(id)) throw new NotFoundException();
    }
}
