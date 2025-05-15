package com.gmail.subscriptions.validation;

import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.exception.ErrorMessage;
import com.gmail.subscriptions.exception.SubscriptionAlreadyExistsException;
import com.gmail.subscriptions.exception.UserHasNoSubscriptionsException;
import com.gmail.subscriptions.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionValidation {

    public void subscriptionExists(User user, SubscriptionDto subscriptionDto) {
        boolean subscriptionExists = user.getSubscriptions().stream()
                .anyMatch(sub -> sub.getName().equals(subscriptionDto.getName()));
        if (subscriptionExists) {
            log.error("Subscription {} for current user {} - already exists!",
                    subscriptionDto.getType(), user.getUserId());
            throw new SubscriptionAlreadyExistsException(ErrorMessage.SUBSCRIPTION_ALREADY_EXISTS.getMessage());
        }
    }

    public void isSubscriptionsExist(UserDto userDto) {
        if (userDto.getSubscriptions() == null || userDto.getSubscriptions().isEmpty()) {
            log.warn("User {} has no existing subscription!", userDto.getUserName());
            throw new UserHasNoSubscriptionsException(ErrorMessage.NO_SUBSCRIPTIONS_FOUND.getMessage());
        }
    }
}
