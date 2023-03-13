package com.example.jdbcwithdaop.Configration.dao.impl;

import com.example.jdbcwithdaop.Configration.dao.UserDao;
import com.example.jdbcwithdaop.Configration.dao.base.Dao;
import com.example.jdbcwithdaop.Configration.dao.base.impl.DaoImpl;
import com.example.jdbcwithdaop.Configration.model.User;

import javax.sql.DataSource;

public class UserDaoImpl extends DaoImpl<User> implements UserDao {
    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
