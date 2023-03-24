package com.example.jdbc_with_dao_pattern.exception.custom_handle;

import com.example.jdbc_with_dao_pattern.dto.response.ErrorResponse;
import com.example.jdbc_with_dao_pattern.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.HandleException.HANDLE_VALIDATION_EXCEPTION;

@Slf4j
@ControllerAdvice
// ten class phai la danh tu
// ten package cang ngan cang tot
public class CustomHandleException {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleException(BaseException ex){
        log.info("(handleException) ex : {}",ex.getCode());
        return new ResponseEntity<>(
                getError(ex.getStatus(),ex.getCode(),ex.getParams()),
                HttpStatus.valueOf(ex.getStatus())
        );
    }
    private ErrorResponse getError(int status, String code, Map<String, String > params) {
        return ErrorResponse.of(status, code, params);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        log.info("(handleValidationExceptions)exception: {}", exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        exception
                .getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });
        log.info("(handleValidationExceptions) {}", errors);
        return new ResponseEntity<>(
                ErrorResponse.of(
                        HttpStatus.BAD_REQUEST.value(),
                        HANDLE_VALIDATION_EXCEPTION,
                        errors),
                HttpStatus.BAD_REQUEST);
    }
}
