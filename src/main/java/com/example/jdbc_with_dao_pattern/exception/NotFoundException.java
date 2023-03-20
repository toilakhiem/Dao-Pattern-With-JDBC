package com.example.jdbc_with_dao_pattern.exception;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ExceptionRespoinse.CONFLICT_EXCEPTION_CODE;
import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ExceptionRespoinse.NOT_FOUND_EXCEPTION;

public class NotFoundException extends BaseException{
    public NotFoundException() {
        setStatus(404);
        setCode(NOT_FOUND_EXCEPTION);
    }
}
