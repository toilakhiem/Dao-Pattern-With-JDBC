package com.example.jdbcwithdaop.dao;

import com.example.jdbcwithdaop.dao.base.Dao;
import com.example.jdbcwithdaop.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    User get(String id);
    List<User> getAll();
    User save(User user);
    User update(User user);
    void delete(String id);
    void bank(String fromUsername, String toUsername, BigDecimal price);
}
