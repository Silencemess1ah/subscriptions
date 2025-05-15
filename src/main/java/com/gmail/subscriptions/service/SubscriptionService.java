package com.gmail.subscriptions.service;

import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.dto.SubscriptionTopDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.mapper.SubscriptionMapper;
import com.gmail.subscriptions.model.Subscription;
import com.gmail.subscriptions.model.User;
import com.gmail.subscriptions.repository.SubscriptionRepository;
import com.gmail.subscriptions.validation.SubscriptionValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserService userService;
    private final SubscriptionValidation subscriptionValidation;

    public SubscriptionDto addSubscriptionToUser(Long userId, SubscriptionDto subscriptionDto) {
        User user = userService.getUserById(userId);
        subscriptionValidation.subscriptionExists(user, subscriptionDto);
        log.info("User {} , has not yet such subscription {} processing...",
                user.getUserId(), subscriptionDto.getType());
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        user.getSubscriptions().add(subscription);
        log.info("Added subscription {} to user {}", subscription.getType(), user.getUserId());
        userService.saveUser(user);
        log.info("Saved subscription {}", subscriptionDto.getType());
        return subscriptionMapper.toDto(subscription);
    }

    public List<SubscriptionDto> getAllUserSubscriptions(Long userId) {
        UserDto userDto = userService.getUserDtoById(userId);
        log.info("Found user {}", userDto.getUserName());
        subscriptionValidation.isSubscriptionsExist(userDto);
        log.info("User has following subscriptions {}", userDto.getSubscriptions().toString());
        return userDto.getSubscriptions();
    }

    public void deleteSubscriptionById(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    public List<SubscriptionTopDto> getTopThreeSubscription() {

        return subscriptionRepository.getTopThreeSubs().stream()
                .map(s -> SubscriptionTopDto.builder().type(s).build())
                .toList();
    }
}
