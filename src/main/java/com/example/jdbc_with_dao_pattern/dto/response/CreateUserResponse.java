package com.example.jdbc_with_dao_pattern.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private String id;
    private String userName;
    private String password;
    private Long price;

    public static CreateUserResponse from(String id, String userName, String password, Long price) {
        var userCreateResponse = new CreateUserResponse();
        userCreateResponse.id = id;
        userCreateResponse.userName = userName;
        userCreateResponse.password = password;
        userCreateResponse.price = price;
        return userCreateResponse;
    }
}
