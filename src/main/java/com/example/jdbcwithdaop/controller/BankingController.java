package com.example.jdbcwithdaop.controller;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.dto.request.BankingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("banking")
public class BankingController {
    @Autowired
    private UserDao userDao;
    @PostMapping
    ResponseEntity<?> bank(@RequestBody BankingRequest request) throws SQLException {
        userDao.bank(request.getFromUsername(), request.getToUsername(), request.getPrice());
        return ResponseEntity.ok("chuyen tien thanh cong");
    }
}
