package com.example.jdbcwithdaop.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankingRequest {
    private String fromUsername;
    private String toUsername;
    private BigDecimal price;
}
