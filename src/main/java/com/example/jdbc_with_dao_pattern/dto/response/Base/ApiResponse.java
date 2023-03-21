package com.example.jdbc_with_dao_pattern.dto.response.Base;

import com.example.jdbc_with_dao_pattern.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ApiResponse {
    private int status;
    private String timestamp;
    private Object data;
    public static ApiResponse of(int status, Object data){
        return ApiResponse.of(status, DateUtils.getCurrentDateString(),data);
    }
    public static ApiResponse of(int status){
        return ApiResponse.of(status, DateUtils.getCurrentDateString(),null);
    }
}
