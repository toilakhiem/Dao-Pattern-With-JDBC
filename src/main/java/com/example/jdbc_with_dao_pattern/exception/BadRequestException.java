package com.example.jdbc_with_dao_pattern.exception;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ExceptionResponse.BAD_REQUEST_EXCEPTION_CODE;

public class BadRequestException extends BaseException{
    public BadRequestException(){
        setCode(BAD_REQUEST_EXCEPTION_CODE);
        setStatus(400);
    }
}
