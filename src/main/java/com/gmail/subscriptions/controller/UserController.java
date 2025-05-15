package com.gmail.subscriptions.controller;


import com.gmail.subscriptions.dto.UserCreationDto;
import com.gmail.subscriptions.dto.UserDto;
import com.gmail.subscriptions.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreationDto userCreationDto) {
        UserDto createdUser = userService.createUser(userCreationDto);

        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(createdUser.getUserId())
                .toUri();

        return ResponseEntity.created(locationUri).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = userService.getUserDtoById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody @Valid UserDto userDto) {
        UserDto updatedUser = userService.updateUserInfo(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
