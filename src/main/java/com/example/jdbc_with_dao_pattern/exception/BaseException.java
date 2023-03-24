package com.example.jdbc_with_dao_pattern.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private int status = 0;
    private String code = "";
    private String message = "";
    private Map<String, String> params;

    public void addParams(String key, String value) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }
}
