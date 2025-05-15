package com.gmail.subscriptions.dto;

import com.gmail.subscriptions.model.SubscriptionType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SubscriptionTopDto {

    private SubscriptionType type;
}
