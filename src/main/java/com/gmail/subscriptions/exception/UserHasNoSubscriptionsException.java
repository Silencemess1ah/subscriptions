package com.gmail.subscriptions.exception;

public class UserHasNoSubscriptionsException extends RuntimeException {
    public UserHasNoSubscriptionsException(String message) {
        super(message);
    }
}