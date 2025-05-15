package com.gmail.subscriptions.service;

import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.dto.SubscriptionTopDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.exception.SubscriptionAlreadyExistsException;
import com.gmail.subscriptions.mapper.SubscriptionMapper;
import com.gmail.subscriptions.model.Subscription;
import com.gmail.subscriptions.model.SubscriptionType;
import com.gmail.subscriptions.model.User;
import com.gmail.subscriptions.repository.SubscriptionRepository;
import com.gmail.subscriptions.validation.SubscriptionValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.subscriptions.model.SubscriptionType.TELEGRAM_PREMIUM;
import static com.gmail.subscriptions.model.SubscriptionType.YOUTUBE_PREMIUM;
import static com.gmail.subscriptions.model.SubscriptionType.SPOTIFY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private SubscriptionValidation subscriptionValidation;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private final Long userIdOne = 1L;
    private final Long subIdOne = 1L;
    private final String testName = "Alice";
    private final String testDescription = "Some description";
    private final SubscriptionType subscriptionType = TELEGRAM_PREMIUM;
    private final String subscriptionName = "Telegram";
    private final List<Subscription> subscriptionList = new ArrayList<>();
    private final List<SubscriptionDto> subscriptionDtoList = new ArrayList<>();
    private final List<SubscriptionType> subscriptionTypeList = new ArrayList<>();
    private User user;
    private UserDto userDto;
    private Subscription subscription;
    private SubscriptionDto subscriptionDto;

    @BeforeEach
    void setUp() {
        subscription = Subscription
                .builder()
                .subId(subIdOne)
                .user(user)
                .description(testDescription)
                .type(subscriptionType)
                .name(subscriptionName)
                .build();

        subscriptionDto = SubscriptionDto
                .builder()
                .userId(userIdOne)
                .description(testDescription)
                .type(subscriptionType)
                .name(subscriptionName)
                .build();

        subscriptionList.add(subscription);
        subscriptionDtoList.add(subscriptionDto);
        subscriptionTypeList.add(SPOTIFY);
        subscriptionTypeList.add(TELEGRAM_PREMIUM);
        subscriptionTypeList.add(YOUTUBE_PREMIUM);

        user = User
                .builder()
                .userId(userIdOne)
                .userName(testName)
                .subscriptions(subscriptionList)
                .build();

        userDto = UserDto
                .builder()
                .userName("_" + testName + "_")
                .subscriptions(subscriptionDtoList)
                .build();
    }


    @Test
    public void whenValidSubscriptionDtoAndUserIdPassedThenAddItToUser() {
        when(userService.getUserById(userIdOne)).thenReturn(user);
        doNothing().when(subscriptionValidation).subscriptionExists(user,
                subscriptionDto);
        when(subscriptionMapper.toEntity(subscriptionDto)).thenReturn(subscription);
        when(subscriptionMapper.toDto(subscription)).thenReturn(subscriptionDto);

        SubscriptionDto result = subscriptionService.addSubscriptionToUser(userIdOne, subscriptionDto);

        assertNotNull(result);
        assertEquals(TELEGRAM_PREMIUM, result.getType());
    }

    @Test
    public void whenUserAlreadyHasSubscriptionThenThrowException() {
        when(userService.getUserById(userIdOne)).thenReturn(user);
        doThrow(new SubscriptionAlreadyExistsException("already exists"))
                .when(subscriptionValidation).subscriptionExists(user, subscriptionDto);

        assertThrows(SubscriptionAlreadyExistsException.class, () ->
                subscriptionService.addSubscriptionToUser(userIdOne, subscriptionDto));
    }

    @Test
    public void whenUserIdPassedThenReturnAllUserSubscriptions() {
        when(userService.getUserDtoById(userIdOne)).thenReturn(userDto);
        doNothing().when(subscriptionValidation).isSubscriptionsExist(userDto);
        List<SubscriptionDto> resultList = subscriptionService.getAllUserSubscriptions(userIdOne);

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(TELEGRAM_PREMIUM, resultList.get(0).getType());
    }

    @Test
    public void whenMethodCalledThenReturnTopMostUsedSubscriptions() {
        when(subscriptionRepository.getTopThreeSubs()).thenReturn(subscriptionTypeList);

        List<SubscriptionTopDto> result = subscriptionService.getTopThreeSubscription();

        assertNotNull(result);
        assertEquals(3, result.size());
    }
}
