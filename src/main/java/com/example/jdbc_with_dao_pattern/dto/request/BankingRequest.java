package com.example.jdbc_with_dao_pattern.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BankingRequest {
    @NotEmpty(message = "thong bao cai dcmm") @NotNull
    private String fromUsername;
    @NotEmpty @NotNull
    private String toUsername;
    @NotNull
    private Long price;
}
