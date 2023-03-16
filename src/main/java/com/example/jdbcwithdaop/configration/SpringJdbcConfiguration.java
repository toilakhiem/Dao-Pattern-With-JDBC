package com.example.jdbcwithdaop.configration;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dao.impl.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.jdbcwithdaop.constant.JdbcWithDaoPatternConstant.ConfigConstant.*;

@Configuration
@ComponentScan(basePackages = "com.example.jdbcwithdaop")
/* Trong trường hợp muốn tùy chỉnh cấu hình cho Spring Boot chỉ tìm kiếm các bean trong một package nhất định */
public class SpringJdbcConfiguration {
    //    @Bean
//    public DataSource mysqlDataSource(){
//        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
//        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
//        dataSource.setUrl(URL);
//        dataSource.setUsername(USER_NAME);
//        dataSource.setPassword(PASSWORD);
//        return dataSource;
//    }
//    public static Connection mysqlDataSource() throws SQLException {
//        Connection connectionToMySql = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//        return connectionToMySql;
//    }

    //    @Bean
//    public UserDao userDao() throws SQLException {
//        return new UserDaoImpl(mysqlDataSource());
//    }
        @Bean
    public UserDaoImpl userDao() throws SQLException {
        return new UserDaoImpl(HikariCPConfiguration.connection());
    }
}
