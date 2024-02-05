package com.example.learntestcode.domain.user.dto.response;

import com.example.learntestcode.domain.user.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        @NotNull Long id,
        @NotBlank String name,
        @NotNull Integer age
) {

    public static UserResponse from(@NotNull UserDto userDto) {
        return new UserResponse(
                userDto.id(),
                userDto.name(),
                userDto.age()
        );
    }
}
