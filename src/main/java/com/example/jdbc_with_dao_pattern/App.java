package com.example.jdbc_with_dao_pattern;

import com.example.jdbc_with_dao_pattern.dao.HikariCPConfiguration;
import com.example.jdbc_with_dao_pattern.configration.WebConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

//@Component
@Slf4j
@Component
public class App {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfiguration.class);
        App app = context.getBean(App.class);
        try(Connection connection = HikariCPConfiguration.getInstance().getConnection()){
            System.out.println(connection);
        }
    }
}
