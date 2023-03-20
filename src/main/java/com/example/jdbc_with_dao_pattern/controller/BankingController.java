package com.example.jdbc_with_dao_pattern.controller;

import com.example.jdbc_with_dao_pattern.dao.UserDao;
import com.example.jdbc_with_dao_pattern.dto.request.BankingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    ResponseEntity<?> bank(@RequestBody @Validated BankingRequest request) throws SQLException {
        userDao.bank(request.getFromUsername(), request.getToUsername(), request.getPrice());
        return ResponseEntity.ok("chuyen tien thanh cong");
    }
}
