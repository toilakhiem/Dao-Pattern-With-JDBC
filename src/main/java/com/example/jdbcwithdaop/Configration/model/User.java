package com.example.jdbcwithdaop.Configration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String user_name;
    private String password;
    private Double price;
}
