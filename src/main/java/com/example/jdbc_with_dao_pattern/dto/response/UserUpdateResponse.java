package com.example.jdbc_with_dao_pattern.dto.response;

import com.example.jdbc_with_dao_pattern.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponse {
    private String id;
    private String userName;
    private String password;
    public static UserUpdateResponse from(User user){
        var userUpdateResponse = new UserUpdateResponse();
        userUpdateResponse.id = user.getId();
        userUpdateResponse.userName = user.getUserName();
        userUpdateResponse.password = user.getPassword();
        return userUpdateResponse;
    }
}
