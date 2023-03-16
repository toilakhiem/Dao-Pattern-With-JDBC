package com.example.jdbcwithdaop.configration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataSource {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_with_dao_pattern";
    private static final String USER = "root";
    private static final String PASS = "toilakhiem";
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER);
        config.setPassword(PASS);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
    private DataSource() { }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
