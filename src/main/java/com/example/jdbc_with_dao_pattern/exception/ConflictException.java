package com.example.jdbc_with_dao_pattern.exception;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.ExceptionResponse.CONFLICT_EXCEPTION_CODE;

public class ConflictException extends BaseException{
    public ConflictException() {
        setStatus(409);
        setCode(CONFLICT_EXCEPTION_CODE);
    }
}
