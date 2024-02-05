package com.example.learntestcode.domain.user.service;

import com.example.learntestcode.domain.user.dto.UserDto;
import com.example.learntestcode.domain.user.dto.request.CreateNewUserRequest;
import com.example.learntestcode.domain.user.entity.User;
import com.example.learntestcode.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    private Optional<User> findById(@NotNull Long id) {
        return userRepository.findById(id);
    }

    public UserDto getDtoById(@NotNull Long id) {
        User user = findById(id).orElseThrow(EntityNotFoundException::new);
        return UserDto.from(user);
    }

    @Transactional
    public UserDto createNewUser(@NotNull CreateNewUserRequest createNewUserRequest) {
        User userSaved = userRepository.save(createNewUserRequest.toEntity());
        return UserDto.from(userSaved);
    }

    @Transactional
    public void deleteById(@NotNull Long id) {
        userRepository.deleteById(id);
    }
}
