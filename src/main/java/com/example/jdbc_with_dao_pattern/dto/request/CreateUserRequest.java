package com.example.jdbc_with_dao_pattern.dto.request;

import com.example.jdbc_with_dao_pattern.validation.ValidatePassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "User name must not be blank")
    private String username;
    @ValidatePassword
    private String password;
    private Long price;
}
