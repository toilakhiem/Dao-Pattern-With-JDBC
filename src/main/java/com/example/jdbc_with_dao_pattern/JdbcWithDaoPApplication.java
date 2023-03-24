package com.example.jdbc_with_dao_pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JdbcWithDaoPApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcWithDaoPApplication.class, args);
    }

}
