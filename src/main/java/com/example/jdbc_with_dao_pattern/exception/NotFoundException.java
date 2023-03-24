package com.example.jdbc_with_dao_pattern.exception;


import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ExceptionResponse.NOT_FOUND_EXCEPTION_CODE;

public class NotFoundException extends BaseException{
    public NotFoundException() {
        setStatus(404);
        setCode(NOT_FOUND_EXCEPTION_CODE);
    }
}
