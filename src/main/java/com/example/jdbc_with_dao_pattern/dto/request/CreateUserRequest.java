package com.example.jdbc_with_dao_pattern.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "User name must not be blank")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*\\W).{6,32}$", message = "Not match")
    private String password;
    private Long price;
}
