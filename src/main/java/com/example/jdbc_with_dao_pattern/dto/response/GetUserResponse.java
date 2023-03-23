package com.example.jdbc_with_dao_pattern.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetUserResponse {
    private String id;
    private String userName;
    private String password;
    private Long price;

    public static GetUserResponse from(String id, String userName, String password, Long price) {
        var getUserResponse = new GetUserResponse();
        getUserResponse.id = id;
        getUserResponse.userName = userName;
        getUserResponse.password = password;
        getUserResponse.price = price;
        return getUserResponse;
    }
}
