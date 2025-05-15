package com.gmail.subscriptions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.dto.SubscriptionTopDto;
import com.gmail.subscriptions.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.gmail.subscriptions.model.SubscriptionType.YOUTUBE_PREMIUM;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionService subscriptionService;

    private final Long userId = 1L;
    private final Long subId = 100L;
    private SubscriptionDto subscriptionDto;
    private SubscriptionTopDto subscriptionTopDto;
    private List<SubscriptionDto> subscriptionDtos;
    private List<SubscriptionTopDto> topDtos;

    @BeforeEach
    void setUp() {
        subscriptionDto = SubscriptionDto.builder()
                .userId(userId)
                .name("YouTube Premium")
                .description("Premium access to YouTube")
                .type(YOUTUBE_PREMIUM)
                .build();

        subscriptionTopDto = SubscriptionTopDto.builder()
                .type(YOUTUBE_PREMIUM)
                .build();

        subscriptionDtos = List.of(subscriptionDto);

        topDtos = List.of(subscriptionTopDto);
    }

    @Test
    void whenAddSubscriptionCalled_thenReturnCreated() throws Exception {
        when(subscriptionService.addSubscriptionToUser(userId, subscriptionDto))
                .thenReturn(subscriptionDto);

        String jsonInput = new ObjectMapper().writeValueAsString(subscriptionDto);

        mockMvc.perform(post("/v1/users/{userId}/subscriptions", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("YOUTUBE_PREMIUM"));
    }

    @Test
    void whenGetSubscriptionsCalled_thenReturnOk() throws Exception {
        when(subscriptionService.getAllUserSubscriptions(userId)).thenReturn(subscriptionDtos);

        mockMvc.perform(get("/v1/users/{userId}/subscriptions", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("YouTube Premium"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenDeleteSubscriptionCalled_thenReturnNoContent() throws Exception {

        mockMvc.perform(delete("/v1/users/{userId}/subscriptions/{subId}", userId, subId))
                .andExpect(status().isNoContent());

        verify(subscriptionService, times(1)).deleteSubscriptionById(subId);
    }

    @Test
    void whenGetTopThreeSubsCalled_thenReturnOk() throws Exception {
        when(subscriptionService.getTopThreeSubscription()).thenReturn(topDtos);

        mockMvc.perform(get("/v1/subscriptions/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("YOUTUBE_PREMIUM"))
                .andExpect(jsonPath("$").isArray());
    }
}
