package com.example.learntestcode.domain.user.dto;

import com.example.learntestcode.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        Long id,
        @NotBlank String name,
        @NotNull Integer age
) {

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge());
    }
}
