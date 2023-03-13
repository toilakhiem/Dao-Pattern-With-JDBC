package com.example.jdbcwithdaop.dao.impl;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dao.base.impl.DaoImpl;
import com.example.jdbcwithdaop.model.User;

import javax.sql.DataSource;

public class UserDaoImpl extends DaoImpl<User> implements UserDao {
    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
