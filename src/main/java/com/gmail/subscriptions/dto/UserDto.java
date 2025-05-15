package com.gmail.subscriptions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long userId;
    @NotBlank
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 characters")
    private String userName;
    private List<SubscriptionDto> subscriptions;
}