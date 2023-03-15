package com.example.jdbcwithdaop.controller;

import com.example.jdbcwithdaop.dao.UserDao;
import com.example.jdbcwithdaop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    private UserDao userDao;
//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody User user){
//        return ResponseEntity.ok().body(userDao.save(user));
//    }
//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody User user){
//        return ResponseEntity.ok().body(userDao.update(user));
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getById(@PathVariable(name = "id")String id){
//        return null;
//    }
//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        return ResponseEntity.ok(userDao.getAll());
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable(name = "id")String id){
//        return null;
//    }
}
