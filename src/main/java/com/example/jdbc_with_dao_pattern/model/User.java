package com.example.jdbc_with_dao_pattern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;
    private Long price;
    public static User of(String id,String userName, String password, Long price){
        var user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setPassword(password);
        user.setPrice(price);
        return user;
    }
}
