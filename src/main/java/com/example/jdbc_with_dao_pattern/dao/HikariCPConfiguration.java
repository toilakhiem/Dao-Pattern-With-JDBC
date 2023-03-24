package com.example.jdbc_with_dao_pattern.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ConfigConstant.*;

public class  HikariCPConfiguration {
    //region Singleton
    // static global HikariCPConfiguration
    private static HikariCPConfiguration instance = new HikariCPConfiguration();
    //private constructor
    private HikariCPConfiguration() {
    }
    // Static factory method
    public static HikariCPConfiguration getInstance(){
        if (instance == null) {
            instance = new HikariCPConfiguration();
        }
        return instance;
    }
    //endregion
    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USER_NAME);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setPoolName("Pool Cua Khiem");
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setIdleTimeout(0);
        hikariConfig.setConnectionTimeout(3000);
        hikariDataSource = new HikariDataSource(hikariConfig);
    }
    private static HikariDataSource hikariDataSource;
    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
