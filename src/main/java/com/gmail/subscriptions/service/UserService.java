package com.gmail.subscriptions.service;

import com.gmail.subscriptions.dto.UserCreationDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.mapper.SubscriptionMapper;
import com.gmail.subscriptions.mapper.UserMapper;
import com.gmail.subscriptions.model.User;
import com.gmail.subscriptions.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SubscriptionMapper subscriptionMapper;

    public UserDto createUser(UserCreationDto userCreationDto) {
        log.info("Creating new user with name {}", userCreationDto.getUserName());
        User newUser = userRepository.save(userMapper.creationToEntity(userCreationDto));
        log.info("Successfully saved user {}", newUser.getUserId());
        return UserDto.builder()
                .userId(newUser.getUserId())
                .userName(newUser.getUserName())
                .build();
    }

    public UserDto getUserDtoById(Long userId) {
        log.info("Searching for user with id {}", userId);
        User userToBeFound = getUserById(userId);

        log.info("Found user {}", userToBeFound.getUserName());
        return UserDto.builder()
                .userId(userToBeFound.getUserId())
                .userName(userToBeFound.getUserName())
                .subscriptions(userToBeFound.getSubscriptions().stream()
                        .map(subscriptionMapper::toDto)
                        .toList())
                .build();
    }

    public UserDto updateUserInfo(UserDto userDto, Long userId) {

        log.info("Searching for user with id {}", userId);
        User userToBeUpdated = getUserById(userId);

        log.info("Found user {}", userToBeUpdated.getUserName());

        userToBeUpdated.setUserName(userDto.getUserName());

        log.info("New user name {}", userToBeUpdated.getUserName());
        User updatedUser = userRepository.save(userToBeUpdated);
        log.info("Successfully updated user {}", updatedUser.getUserName());
        return UserDto.builder()
                .userId(updatedUser.getUserId())
                .userName(updatedUser.getUserName())
                .build();
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        log.info("Deleted user {}", userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
