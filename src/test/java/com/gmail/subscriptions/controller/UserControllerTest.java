package com.gmail.subscriptions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.subscriptions.dto.UserCreationDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final Long userIdOne = 1L;
    private final String testUserName = "Alice";
    private final String testUserUpdatedName = "Bob";
    private UserDto getUserDto;
    private UserCreationDto creationDto;
    private UserDto inputDto;
    private UserDto outputDto;

    @BeforeEach
    void setUp() {
        getUserDto = UserDto.builder()
                .userId(userIdOne)
                .userName(testUserName)
                .build();

        creationDto = UserCreationDto.builder()
                .userName(testUserName)
                .build();

        inputDto = UserDto
                .builder()
                .userId(userIdOne)
                .userName(testUserUpdatedName)
                .subscriptions(null)
                .build();

        outputDto = UserDto
                .builder()
                .userId(userIdOne)
                .userName(testUserUpdatedName)
                .subscriptions(new ArrayList<>())
                .build();
    }


    @Test
    public void whenGetUserCalledThenReturnOk() throws Exception {

        when(userService.getUserDtoById(userIdOne)).thenReturn(getUserDto);

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(testUserName));
    }

    @Test
    public void whenCreateUserCalledThenReturnCreated() throws Exception {
        when(userService.createUser(any(UserCreationDto.class))).thenReturn(getUserDto);

        String json = new ObjectMapper().writeValueAsString(creationDto);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(testUserName));
    }

    @Test
    public void whenUserIdPassedThenReturnNoContent() throws Exception {

        mockMvc.perform(delete("/v1/users/{userId}", userIdOne))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenUserIdAndDtoPassedThenReturnOk() throws Exception {

        when(userService.updateUserInfo(inputDto, userIdOne)).thenReturn(outputDto);

        String jsonInput = new ObjectMapper().writeValueAsString(inputDto);

        mockMvc.perform(put("/v1/users/{userId}", userIdOne)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(testUserUpdatedName))
                .andExpect(jsonPath("$.userId").value(userIdOne));
    }
}