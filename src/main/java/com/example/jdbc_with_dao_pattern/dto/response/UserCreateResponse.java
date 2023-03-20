package com.example.jdbc_with_dao_pattern.dto.response;

import com.example.jdbc_with_dao_pattern.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateResponse {
    private String id;
    private String userName;
    private String password;
    public static UserCreateResponse from(User user){
        var userCreateResponse = new UserCreateResponse();
        userCreateResponse.id = user.getId();
        userCreateResponse.userName = user.getUserName();
        userCreateResponse.password = user.getPassword();
        return userCreateResponse;
    }
}
