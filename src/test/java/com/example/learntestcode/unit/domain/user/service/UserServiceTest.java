package com.example.learntestcode.unit.domain.user.service;

import com.example.learntestcode.domain.user.dto.UserDto;
import com.example.learntestcode.domain.user.dto.request.CreateNewUserRequest;
import com.example.learntestcode.domain.user.entity.User;
import com.example.learntestcode.domain.user.repository.UserRepository;
import com.example.learntestcode.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("[Unit] Service - User")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    @Test
    void 주어진_id로_단일_회원을_조회한다() throws Exception {
        // given
        long userId = 1L;
        User expectedResult = createUser(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(expectedResult));

        // when
        UserDto actualResult = sut.getDtoById(userId);

        // then
        then(userRepository).should().findById(userId);
        then(userRepository).shouldHaveNoMoreInteractions();
        assertThat(actualResult)
                .hasFieldOrPropertyWithValue("id", expectedResult.getId())
                .hasFieldOrPropertyWithValue("name", expectedResult.getName())
                .hasFieldOrPropertyWithValue("age", expectedResult.getAge());
    }

    @Test
    void 주어진_id로_단일_회원을_조회한다_일치하는_회원이_없을_경우_예외가_발생한다() {
        // given
        long userId = 100L;

        // when
        Throwable t = catchThrowable(() -> sut.getDtoById(userId));

        // then
        assertThat(t).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 주어진_신규_회원_정보로_회원을_등록한다() throws Exception {
        // given
        CreateNewUserRequest createNewUserReq = new CreateNewUserRequest("test", 20);
        User expectedResult = createUser(1L);
        given(userRepository.save(any(User.class))).willReturn(expectedResult);

        // when
        UserDto actualResult = sut.createNewUser(createNewUserReq);

        // then
        then(userRepository).should().save(any(User.class));
        then(userRepository).shouldHaveNoMoreInteractions();
        assertThat(actualResult)
                .hasFieldOrPropertyWithValue("id", expectedResult.getId())
                .hasFieldOrPropertyWithValue("name", expectedResult.getName())
                .hasFieldOrPropertyWithValue("age", expectedResult.getAge());
    }

    @Test
    void 주어진_id에_해당하는_회원을_삭제한다() {
        // given
        long userId = 1L;
        willDoNothing().given(userRepository).deleteById(userId);

        // when
        sut.deleteById(userId);

        // then
        then(userRepository).should().deleteById(userId);
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    private User createUser(Long id, String name, int age) throws Exception {
        Constructor<User> userConstructor = User.class.getDeclaredConstructor(Long.class, String.class, Integer.class);
        userConstructor.setAccessible(true);
        return userConstructor.newInstance(id, name, age);
    }

    private User createUser(Long id) throws Exception {
        return createUser(id, "test", 20);
    }
}