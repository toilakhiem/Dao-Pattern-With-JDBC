package com.example.jdbc_with_dao_pattern.constant;

public class JdbcWithDaoPatternConstant {
    public static class ConfigConstant{
        public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
        public static final String URL = "jdbc:mysql://localhost:3306/jdbc_with_dao_pattern";
        public static final String USER_NAME = "root";
        public static final String PASSWORD = "toilakhiem";
    }
    public static class QueryOfMysql{
        public static final String CREATE_USER = "INSERT INTO users (id, user_name, password, price) VALUES ('%s', '%s', '%s', %s) ";
        public static final String GET_USER_BY_ID = "SELECT * FROM users where users.id = '%s'";
        public static final String GET_USER_BY_USERNAME = "SELECT * FROM users where users.user_name = '%s'";
        public static final String GET_ALL_USER = "SELECT * FROM users;";
        public static final String UPDATE_USER_BY_ID = "UPDATE users SET user_name='%s', password='%s', price=%s WHERE id= '%s'";
        public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE users.id=";
        public static final String INCREASE_PRICE_OF_RECEIVED_USER = "UPDATE users SET price = price + %s where user_name = '%s'";
        public static final String DECREASE_PRICE_OF_SEND_USER = "UPDATE users SET price = price - %s where user_name = '%s'";
        public static final String EXIST_BY_USER_NAME = "SELECT 1 FROM users WHERE users.user_name = '%s' limit 1";
        public static final String EXIST_BY_ID = "SELECT 1 from users WHERE users.id = '%s' limit 1";
        public static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE users.user_name = '%s'";
    }
    public static class ExceptionResponse{
        public static final String CONFLICT_EXCEPTION_CODE = "Conflict Exception";
        public static final String NOT_FOUND_EXCEPTION_CODE = "Not Found Exception";
        public static final String BAD_REQUEST_EXCEPTION_CODE = "Bad Request Exception";
    }
    public static class HandleException{
        public static final String HANDLE_VALIDATION_EXCEPTION = "HandleValidationExceptions";
    }
    public static class Validation{
        public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*\\W).{6,32}$";
    }
}
