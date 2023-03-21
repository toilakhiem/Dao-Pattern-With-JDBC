package com.example.jdbc_with_dao_pattern.configration;

import com.example.jdbc_with_dao_pattern.service.impl.UserDaoImpl;
import com.example.jdbc_with_dao_pattern.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "com.example.jdbc_with_dao_pattern")
/* Trong trường hợp muốn tùy chỉnh cấu hình cho Spring Boot chỉ tìm kiếm các bean trong một package nhất định */
public class WebConfiguration {
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
    //khoi tao kieu nay ton connection
    @Bean
    public UserDaoImpl userDao() throws SQLException {
        return new UserDaoImpl(HikariCPConfiguration.getInstance().getDataSource());
    }
    @Bean
    public UserValidation userValidation() {
        return new UserValidation(HikariCPConfiguration.getInstance().getDataSource());
    }
}
