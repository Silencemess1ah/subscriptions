package com.gmail.subscriptions.controller;

import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.dto.SubscriptionTopDto;
import com.gmail.subscriptions.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<SubscriptionDto> addSubscription(@PathVariable Long userId,
                                                           @RequestBody @Valid SubscriptionDto subscriptionDto) {
        SubscriptionDto savedSubscription = subscriptionService.addSubscriptionToUser(userId, subscriptionDto);
        return new ResponseEntity<>(savedSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(@PathVariable Long userId) {

        List<SubscriptionDto> userSubscriptions = subscriptionService.getAllUserSubscriptions(userId);
        return ResponseEntity.ok(userSubscriptions);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId, @PathVariable Long subId) {
        subscriptionService.deleteSubscriptionById(subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<SubscriptionTopDto>> getTopThreeSubs() {
        List<SubscriptionTopDto> topSubscriptions = subscriptionService.getTopThreeSubscription();
        return ResponseEntity.ok(topSubscriptions);
    }

}
