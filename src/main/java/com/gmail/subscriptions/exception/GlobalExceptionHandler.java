package com.gmail.subscriptions.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ErrorMessage.USER_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionAlreadyExists(SubscriptionAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(),
                ErrorMessage.SUBSCRIPTION_ALREADY_EXISTS.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserHasNoSubscriptionsException.class)
    public ResponseEntity<ErrorResponse> handleUserHasNoSubscriptions(UserHasNoSubscriptionsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ErrorMessage.NO_SUBSCRIPTIONS_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorMessage.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
