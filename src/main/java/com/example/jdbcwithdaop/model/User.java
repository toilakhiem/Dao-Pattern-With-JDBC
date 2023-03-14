package com.example.jdbcwithdaop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String user_name;
    private String password;
    private BigDecimal price;
}
