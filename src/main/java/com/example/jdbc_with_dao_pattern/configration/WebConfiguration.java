package com.example.jdbc_with_dao_pattern.configration;

import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.dao.impl.UserDaoImpl;
import com.example.jdbc_with_dao_pattern.service.UserService;
import com.example.jdbc_with_dao_pattern.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "com.example.jdbc_with_dao_pattern")
/* Trong trường hợp muốn tùy chỉnh cấu hình cho Spring Boot chỉ tìm kiếm các bean trong một package nhất định */
public class WebConfiguration {
    @Bean
    public UserDao userDao(){
        return new UserDaoImpl();
    }
    @Bean
    public UserService userValidation(){
        return new UserServiceImpl(userDao());
    }
}
