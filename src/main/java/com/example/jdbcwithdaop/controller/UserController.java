package com.example.jdbcwithdaop.controller;

import com.example.jdbcwithdaop.Configration.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @PostMapping
    public ResponseEntity<?> create(){
        return null;
    }
    @PutMapping
    public ResponseEntity<?> update(){
        return null;
    }
    @GetMapping
    public ResponseEntity<?> getById(@PathVariable(name = "id")String id){
        return null;
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        return null;
    }
}
