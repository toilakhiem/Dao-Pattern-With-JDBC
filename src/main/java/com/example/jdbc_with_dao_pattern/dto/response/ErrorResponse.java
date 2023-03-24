package com.example.jdbc_with_dao_pattern.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@Data
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String code;
    private Object data;
}
