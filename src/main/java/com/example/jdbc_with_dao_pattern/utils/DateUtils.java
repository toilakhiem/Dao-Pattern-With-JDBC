package com.example.jdbc_with_dao_pattern.utils;

import java.time.LocalDate;

public class DateUtils {
    public static String getCurrentDateString() {
        return LocalDate.now().toString();
    }
}
