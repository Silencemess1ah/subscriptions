package com.gmail.subscriptions.dto;

import com.gmail.subscriptions.model.SubscriptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDto {

    @NotNull
    private Long userId;
    @NotBlank(message = "Subscription name is required")
    @Size(min = 3, max = 32, message = "Sub name must be between 3 and 32 characters")
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private SubscriptionType type;
}
