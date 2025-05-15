package com.gmail.subscriptions.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    USER_NOT_FOUND("User not found"),
    SUBSCRIPTION_ALREADY_EXISTS("Subscription already exists for this user"),
    INVALID_REQUEST("Invalid request data"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_SUBSCRIPTIONS_FOUND("User has no subscriptions");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
