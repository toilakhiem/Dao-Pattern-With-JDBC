package com.example.jdbc_with_dao_pattern.service;

import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.dto.response.CreateUserResponse;
import com.example.jdbc_with_dao_pattern.dto.response.GetAllUserResponse;
import com.example.jdbc_with_dao_pattern.dto.response.GetUserResponse;
import com.example.jdbc_with_dao_pattern.dto.response.UpdateUserResponse;
import com.example.jdbc_with_dao_pattern.exception.ConflictException;
import com.example.jdbc_with_dao_pattern.exception.NotFoundException;
import com.example.jdbc_with_dao_pattern.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public CreateUserResponse create(String userName, String password, Long price) throws SQLException {
        String id = UUID.randomUUID().toString();
        validUserNameExisted(userName);
        price = Objects.isNull(price) ? 0L : price;
        User user = User.of(id, userName, password, price);
        user = userDao.save(user);
        return CreateUserResponse.from(
                user.getId(), user.getUserName(), user.getPassword(), user.getPrice()
        );
    }

    @Override
    public UpdateUserResponse update(String id, String password, Long price) throws SQLException {
        validNotFoundById(id);
        User user = userDao.update(userDao.get(id));
        return UpdateUserResponse.from(
                user.getId(), user.getUserName(), user.getPassword(), user.getPrice()
        );
    }

    @Override
    public GetUserResponse get(String id) throws SQLException {
        validNotFoundById(id);
        User user = userDao.get(id);
        return GetUserResponse.from(user.getId(), user.getUserName(), user.getPassword(), user.getPrice());
    }

    @Override
    public GetAllUserResponse getAll() throws SQLException {
        GetAllUserResponse getAllUserResponse = new GetAllUserResponse();
        List<User> result = userDao.getAll();
        result.stream().forEach(
                i ->{
                    GetUserResponse getUserResponse = GetUserResponse.from(i.getId(),i.getUserName(),i.getPassword(),i.getPrice());
                    getAllUserResponse.getAllUsers().add(getUserResponse);
                }
        );
        return getAllUserResponse;
    }

    @Override
    public void delete(String id) throws SQLException {
        validNotFoundById(id);
        userDao.delete(id);
    }


    private void validUserNameExisted(String userName) throws SQLException {
        if (userDao.existByUserName(userName)) throw new ConflictException();
    }

    public void validNotFoundById(String id) throws SQLException {
        if (!userDao.existById(id)) throw new NotFoundException();
    }
}
