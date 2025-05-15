package com.gmail.subscriptions.service;

import com.gmail.subscriptions.dto.SubscriptionDto;
import com.gmail.subscriptions.dto.UserCreationDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.mapper.SubscriptionMapper;
import com.gmail.subscriptions.mapper.UserMapper;
import com.gmail.subscriptions.model.Subscription;
import com.gmail.subscriptions.model.SubscriptionType;
import com.gmail.subscriptions.model.User;
import com.gmail.subscriptions.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gmail.subscriptions.model.SubscriptionType.TELEGRAM_PREMIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private UserService userService;

    private final Long userIdOne = 1L;
    private final Long subIdOne = 1L;
    private final String testName = "Alice";
    private final String testDescription = "Some description";
    private final SubscriptionType subscriptionType = TELEGRAM_PREMIUM;
    private final String subscriptionName = "Telegram";
    private final List<Subscription> subscriptionList = new ArrayList<>();
    private UserCreationDto userCreationDto;
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

        userCreationDto = UserCreationDto
                .builder()
                .userName(testName)
                .build();

        user = User
                .builder()
                .userId(userIdOne)
                .userName(testName)
                .subscriptions(subscriptionList)
                .build();

        userDto = UserDto
                .builder()
                .userName("_" + testName + "_")
                .build();
    }

    @Test
    void whenValidCreationDtoPassedThenSaveUserToDbAndReturnUserDto() {
        when(userMapper.creationToEntity(userCreationDto)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto result = userService.createUser(userCreationDto);
        assertNotNull(result);
        assertEquals(testName, result.getUserName());
        verify(userMapper).creationToEntity(userCreationDto);
        verify(userRepository).save(user);
    }

    @Test
    public void whenUserDoesNotExistsInDbThenThrowEntityNotFoundException() {
        when(userRepository.findById(userIdOne)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userIdOne));
    }

    @Test
    public void whenUserIdPassedThenReturnUserFromDb() {
        when(userRepository.findById(userIdOne)).thenReturn(Optional.of(user));
        when(subscriptionMapper.toDto(any(Subscription.class))).thenReturn(subscriptionDto);

        UserDto resultDto = userService.getUserDtoById(userIdOne);

        assertNotNull(resultDto);
        assertEquals(testName, resultDto.getUserName());
        assertEquals(1, resultDto.getSubscriptions().size());
        assertEquals(TELEGRAM_PREMIUM, resultDto.getSubscriptions().get(0).getType());
    }

    @Test
    public void whenUserIdAndValidDtoPassedThenUpdateUserNameAndSaveToDb() {
        when(userRepository.findById(userIdOne)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        UserDto resultDto = userService.updateUserInfo(userDto, userIdOne);
        assertEquals("_" + testName + "_", resultDto.getUserName());
    }
}