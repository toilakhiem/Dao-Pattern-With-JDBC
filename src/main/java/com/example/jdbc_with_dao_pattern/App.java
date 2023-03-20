package com.example.jdbc_with_dao_pattern;

import com.example.jdbc_with_dao_pattern.configration.SpringJdbcConfiguration;
import com.example.jdbc_with_dao_pattern.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

//@Component
@Slf4j
@Component
public class App {
    @Autowired
    private UserDao userDao;

    public void test() throws SQLException {
        System.out.println(userDao.get("1"));

    }

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfiguration.class);
        App app = context.getBean(App.class);
        app.test();
    }
}
