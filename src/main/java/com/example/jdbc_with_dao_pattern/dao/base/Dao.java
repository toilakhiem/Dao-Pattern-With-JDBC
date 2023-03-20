package com.example.jdbc_with_dao_pattern.dao.base;

import java.util.List;

public interface Dao<T>{
    T get(String id);
    List<T> getAll();
    void save(T t);
    void updat(T t);
    void delete(String id);
}
