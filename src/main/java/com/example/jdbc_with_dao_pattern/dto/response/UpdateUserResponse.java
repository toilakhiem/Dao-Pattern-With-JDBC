package com.example.jdbc_with_dao_pattern.dto.response;

import com.example.jdbc_with_dao_pattern.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    private String id;
    private String userName;
    private String password;
    private Long price;
    public static UpdateUserResponse from(String id, String userName, String password, Long price){
        var userUpdateResponse = new UpdateUserResponse();
        userUpdateResponse.id = id;
        userUpdateResponse.userName = userName;
        userUpdateResponse.password = password;
        userUpdateResponse.price = price;
        return userUpdateResponse;
    }
}
