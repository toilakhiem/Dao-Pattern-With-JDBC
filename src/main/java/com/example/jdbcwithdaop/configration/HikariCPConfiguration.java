package com.example.jdbcwithdaop.configration;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dao.impl.UserDaoImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.jdbcwithdaop.constant.JdbcWithDaoPatternConstant.ConfigConstant.*;
//@Configuration
//@ComponentScan(basePackages = "com.example.jdbcwithdaop")
public class HikariCPConfiguration {
    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource hikariDataSource;
//    static HikariDataSource mysqlDataSource() throws SQLException {
//        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
//        hikariConfig.setJdbcUrl(URL);
//        hikariConfig.setUsername(USER_NAME);
//        hikariConfig.setPassword(PASSWORD);
//        hikariDataSource = new HikariDataSource(hikariConfig);
//        return hikariDataSource;
//    }
    static {
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USER_NAME);
        hikariConfig.setPassword(PASSWORD);
//        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
//        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
//        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    private HikariCPConfiguration() {
    }
    @Bean
    public static Connection connection() throws SQLException {
        return hikariDataSource.getConnection();
    }
//    @Bean
//    public UserDao userDao() throws SQLException {
//        return new UserDaoImpl(HikariCPConfiguration.connection());
//    }
}
