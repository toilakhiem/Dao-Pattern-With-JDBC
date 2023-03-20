package com.example.jdbc_with_dao_pattern.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteUserRequest {
    private String id;
}
