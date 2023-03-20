package com.example.jdbc_with_dao_pattern.configration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ConfigConstant.*;

public class HiakriCPDataSource {
    private static HikariDataSource hikariDataSource;
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

    private HiakriCPDataSource() {
    }

    public static HikariDataSource getInstance() {
        return hikariDataSource;
    }

    public static Connection connection() throws SQLException {
        return hikariDataSource.getConnection();
    }

}
