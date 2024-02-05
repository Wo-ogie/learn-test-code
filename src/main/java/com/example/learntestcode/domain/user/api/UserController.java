package com.example.learntestcode.domain.user.api;

import com.example.learntestcode.domain.user.dto.UserDto;
import com.example.learntestcode.domain.user.dto.request.CreateNewUserRequest;
import com.example.learntestcode.domain.user.dto.response.UserResponse;
import com.example.learntestcode.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(
            @RequestBody @Valid CreateNewUserRequest createNewUserRequest
    ) {
        UserDto newUser = userService.createNewUser(createNewUserRequest);
        return ResponseEntity
                .created(URI.create(String.format("/users/%d", newUser.id())))
                .body(UserResponse.from(newUser));
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getDtoById(userId);
        return UserResponse.from(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
