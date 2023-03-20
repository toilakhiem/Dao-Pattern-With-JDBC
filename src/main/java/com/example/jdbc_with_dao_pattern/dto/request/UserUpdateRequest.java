package com.example.jdbc_with_dao_pattern.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank
    private String id;
    @NotEmpty
    private String newPassword;
    private Long newPrice;
}
