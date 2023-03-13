package com.example.jdbcwithdaop.Configration;

import com.example.jdbcwithdaop.Configration.dao.UserDao;
import com.example.jdbcwithdaop.Configration.dao.base.Dao;
import com.example.jdbcwithdaop.Configration.dao.impl.UserDaoImpl;
import com.example.jdbcwithdaop.Configration.dao.impl.UserDaoImpl2;
import com.example.jdbcwithdaop.Configration.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.example.jdbcwithdaop")
/* Trong trường hợp muốn tùy chỉnh cấu hình cho Spring Boot chỉ tìm kiếm các bean trong một package nhất định */
public class SpringJdbcConfig {
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/jdbc_with_dao_pattern";
    private final String USER_NAME = "root";
    private final String PASSWORD = "toilakhiem";
    @Bean
    public DataSource mysqlDataSource(){
        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
    @Bean
    public UserDao userDao(){
        return new UserDaoImpl(mysqlDataSource());
    }
    @Bean
    public Dao<User> userDao2(){
        return new UserDaoImpl2(mysqlDataSource());
    }
}
