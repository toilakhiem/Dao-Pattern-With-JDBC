package com.example.jdbc_with_dao_pattern.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserUpdateRequest {
    @NotNull @NotEmpty
    private String id;
    @NotEmpty
    private String newPassword;
    private Long newPrice;
}
