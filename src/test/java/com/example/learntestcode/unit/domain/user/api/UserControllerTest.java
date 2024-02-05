package com.example.learntestcode.unit.domain.user.api;

import com.example.learntestcode.domain.user.api.UserController;
import com.example.learntestcode.domain.user.dto.UserDto;
import com.example.learntestcode.domain.user.dto.request.CreateNewUserRequest;
import com.example.learntestcode.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Unit] Controller - User")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void 주어진_신규_회원_정보로_회원을_등록한다() throws Exception {
        // given
        long userId = 1L;
        CreateNewUserRequest createNewUserReq = new CreateNewUserRequest("test", 20);
        UserDto expectedResult = createUserDto(userId);
        given(userService.createNewUser(any(CreateNewUserRequest.class))).willReturn(expectedResult);

        // when & then
        mvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createNewUserReq))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResult.id()))
                .andExpect(jsonPath("$.name").value(expectedResult.name()))
                .andExpect(jsonPath("$.age").value(expectedResult.age()));
        then(userService).should().createNewUser(any(CreateNewUserRequest.class));
        then(userService).shouldHaveNoMoreInteractions();
    }

    @Test
    void 주어진_id로_단일_회원을_조회한다() throws Exception {
        // given
        long userId = 1L;
        UserDto expectedResult = createUserDto(userId);
        given(userService.getDtoById(userId)).willReturn(expectedResult);

        // when & then
        mvc.perform(
                        get(String.format("/users/%d", userId))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedResult.id()))
                .andExpect(jsonPath("$.name").value(expectedResult.name()))
                .andExpect(jsonPath("$.age").value(expectedResult.age()));
        then(userService).should().getDtoById(userId);
        then(userService).shouldHaveNoMoreInteractions();
    }

    @Test
    void 주어진_id에_해당하는_회원을_삭제한다() throws Exception {
        // given
        long userId = 1L;
        willDoNothing().given(userService).deleteById(userId);

        // when & then
        mvc.perform(
                        delete(String.format("/users/%d", userId))
                )
                .andExpect(status().isNoContent());
        then(userService).should().deleteById(userId);
        then(userService).shouldHaveNoMoreInteractions();
    }

    private UserDto createUserDto(Long id) {
        return new UserDto(id, "test", 20);
    }
}