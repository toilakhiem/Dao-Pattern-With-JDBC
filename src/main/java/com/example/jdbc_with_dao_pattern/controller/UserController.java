package com.example.jdbc_with_dao_pattern.controller;

import com.example.jdbc_with_dao_pattern.dto.request.CreateUserRequest;
import com.example.jdbc_with_dao_pattern.dto.request.UserUpdateRequest;
import com.example.jdbc_with_dao_pattern.dto.response.Base.ApiResponse;
import com.example.jdbc_with_dao_pattern.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse create(@RequestBody @Validated CreateUserRequest request) throws SQLException {
        return ApiResponse.of(
                HttpStatus.CREATED.value(),
                userService.create(request.getUsername(), request.getPassword(), request.getPrice())
        );
    }

    @PutMapping
    public ApiResponse update(@RequestBody @Validated UserUpdateRequest request) throws SQLException {
        return ApiResponse.of(
                HttpStatus.OK.value(),
                userService.update(request.getId(), request.getNewPassword(), request.getNewPrice())
        );
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable(name = "id") String id) throws SQLException {
        return ApiResponse.of(
                HttpStatus.OK.value(),
                userService.get(id)
        );
    }

    @GetMapping
    public ApiResponse getAllUser() throws SQLException {
        return ApiResponse.of(
                HttpStatus.OK.value(),
                userService.getAll()
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable(name = "id") String id) throws SQLException {
        userService.delete(id);
        return ApiResponse.of(
                HttpStatus.OK.value()
        );
    }

}
