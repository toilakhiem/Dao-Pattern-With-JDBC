package com.example.jdbcwithdaop;

import com.example.jdbcwithdaop.configration.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String... args) throws SQLException {
        fetchData();
    }

    public static void fetchData() throws SQLException {
        try (Connection con = DataSource.getConnection()){
            System.out.println(con);
        }
    }
}
