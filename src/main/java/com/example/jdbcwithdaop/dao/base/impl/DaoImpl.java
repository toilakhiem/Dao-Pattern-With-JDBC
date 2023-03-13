package com.example.jdbcwithdaop.dao.base.impl;


import com.example.jdbcwithdaop.dao.base.Dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class DaoImpl<T> implements Dao<T> {
    private JdbcTemplate jdbcTemplate;
    private final Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    public DaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public T get(String id) {
        String tableName = persistentClass.getSimpleName().toLowerCase() + "s";
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        return (T) jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                try {
                    T instance = persistentClass.newInstance();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int index = 1; index <= columnCount; index++) {
                        String columnLabel = metaData.getColumnLabel(index);
                        Field field = persistentClass.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(instance, resultSet.getObject(columnLabel));
                    }
                    return instance;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public List<T> getAll() {
//        String sql = "SELECT * FROM contact";
//        List<User> contacts = jdbcTemplate.query(sql, new RowMapper<Contact>() {
//
//            @Override
//            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Contact contact = new Contact();
//                contact.setId(rs.getInt("contact_id"));
//                contact.setName(rs.getString("name"));
//                contact.setEmail(rs.getString("email"));
//                contact.setAddress(rs.getString("address"));
//                contact.setTelephone(rs.getString("telephone"));
//
//                return contact;
//            }
//
//        });
//        return contacts;
        return null;
    }

    @Override
    public void save(T t) {

    }

    @Override
    public void updat(T t) {

    }

    @Override
    public void delete(String id) {

    }
}
