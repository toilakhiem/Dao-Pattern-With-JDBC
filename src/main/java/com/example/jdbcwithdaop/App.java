package com.example.jdbcwithdaop;

import com.example.jdbcwithdaop.configration.HikariCPConfiguration;
import com.example.jdbcwithdaop.configration.SpringJdbcConfiguration;
import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dao.impl.UserDaoImpl;
import com.example.jdbcwithdaop.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.jdbcwithdaop.constant.JdbcWithDaoPatternConstant.QueryOfMysql.GET_USER_BY_ID;

//@Component
@Slf4j
@Component
public class App {
    @Autowired
    private UserDaoImpl userDao;
    public App() throws SQLException {
    }

    public void test() throws SQLException {
//        User user = userDao2.get("1");
//        System.out.println(user);
//        userDao2.save(new User("1","khiem2","khiem123",10.0));
//        log.info("GET ALL ---------------------------------------------------");
//        List<User> users = userDao2.getAll();
//        System.out.println(users);
//        System.out.println(userDao.get("1"));
        UserDaoImpl userDao1 = new UserDaoImpl(HikariCPConfiguration.connection());
        userDao1.testConnection();
    }

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfiguration.class);
        App app = context.getBean(App.class);
        app.test();
        Connection connection = HikariCPConfiguration.connection();
        User user = new User();
        Statement statement = connection.createStatement();
        String sql = GET_USER_BY_ID + "1";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setPrice(rs.getBigDecimal("price"));
        }
        connection.close();
        System.out.println(user);
    }
}
