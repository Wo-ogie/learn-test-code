package com.example.learntestcode.integration.domain.user.repository;

import com.example.learntestcode.domain.user.entity.User;
import com.example.learntestcode.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("[Integration] Repository - User")
@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    private final UserRepository sut;

    @Autowired
    public UserRepositoryTest(UserRepository sut) {
        this.sut = sut;
    }

    @BeforeEach
    void setTestData() {
        sut.save(User.create("test", 20));
    }

    @Test
    void 주어진_id로_단일_회원을_조회한다() {
        // given
        long userId = 1L;

        // when
        Optional<User> result = sut.findById(userId);

        // then
        assertThat(result).isNotEmpty();
    }
}