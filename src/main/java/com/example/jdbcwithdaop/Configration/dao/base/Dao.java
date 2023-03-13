package com.example.jdbcwithdaop.Configration.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<T>{
    T get(String id);
    List<T> getAll();
    void save(T t);
    void updat(T t);
    void delete(String id);
}
