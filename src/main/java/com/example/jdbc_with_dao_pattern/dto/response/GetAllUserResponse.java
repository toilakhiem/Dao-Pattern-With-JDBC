package com.example.jdbc_with_dao_pattern.dto.response;

import com.example.jdbc_with_dao_pattern.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetAllUserResponse {
    private List<GetUserResponse> allUsers;
}

