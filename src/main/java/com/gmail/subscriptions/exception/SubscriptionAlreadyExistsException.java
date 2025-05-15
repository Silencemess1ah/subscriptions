package com.gmail.subscriptions.exception;

public class SubscriptionAlreadyExistsException extends RuntimeException {
    public SubscriptionAlreadyExistsException(String message) {
        super(message);
    }
}