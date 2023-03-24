package com.example.jdbc_with_dao_pattern.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetAllUserResponse {
    private List<GetUserResponse> allUsers = new ArrayList<>();
}

