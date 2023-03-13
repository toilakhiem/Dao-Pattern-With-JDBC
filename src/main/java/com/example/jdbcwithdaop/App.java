package com.example.jdbcwithdaop;

import com.example.jdbcwithdaop.Configration.SpringJdbcConfig;
import com.example.jdbcwithdaop.Configration.dao.UserDao;
import com.example.jdbcwithdaop.Configration.dao.base.Dao;
import com.example.jdbcwithdaop.Configration.dao.impl.UserDaoImpl2;
import com.example.jdbcwithdaop.Configration.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class App {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Dao<User> userDao2;
    public void test(){
//        User user = userDao2.get("1");
//        System.out.println(user);
//        userDao2.save(new User("1","khiem2","khiem123",10.0));
//        log.info("GET ALL ---------------------------------------------------");
//        List<User> users = userDao2.getAll();
//        System.out.println(users);
        userDao2.delete("1");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);
        App app = context.getBean(App.class);
        app.test();
    }
}
