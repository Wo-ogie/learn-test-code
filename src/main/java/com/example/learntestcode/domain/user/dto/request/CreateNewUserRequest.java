package com.example.learntestcode.domain.user.dto.request;

import com.example.learntestcode.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNewUserRequest(
        @NotBlank String name,
        @NotNull Integer age
) {

    public User toEntity() {
        return User.create(name, age);
    }
}
