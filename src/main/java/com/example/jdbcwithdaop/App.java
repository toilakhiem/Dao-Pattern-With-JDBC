package com.example.jdbcwithdaop;

import com.example.jdbcwithdaop.configration.SpringJdbcConfig;
import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dao.impl.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Slf4j
public class App {
    private final UserDao userDao;

    public App(UserDao userDao) {
        this.userDao = userDao;
    }

    public void test() throws SQLException {
//        User user = userDao2.get("1");
//        System.out.println(user);
//        userDao2.save(new User("1","khiem2","khiem123",10.0));
//        log.info("GET ALL ---------------------------------------------------");
//        List<User> users = userDao2.getAll();
//        System.out.println(users);
        System.out.println(userDao.get("1"));
        userDao.testConnection();
    }

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);
        App app = context.getBean(App.class);
        app.test();
    }
}
