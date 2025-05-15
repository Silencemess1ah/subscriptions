package com.gmail.subscriptions.mapper;

import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    @Mapping(target = "user.userId", source = "userId")
    Subscription toEntity(SubscriptionDto subscriptionDto);

    @Mapping(target = "userId", source = "user.userId")
    SubscriptionDto toDto(Subscription subscription);

}
