package com.bci.api.controller;

import com.bci.api.dto.request.UserCreateRequest;
import com.bci.api.dto.request.UserPartialUpdateRequest;
import com.bci.api.dto.request.UserUpdateRequest;
import com.bci.api.dto.response.UserCreateResponse;
import com.bci.api.dto.response.UserResponse;
import com.bci.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserCreateResponse> registerUser(@Valid @RequestBody UserCreateRequest userRequest) {
        UserCreateResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUserById(@RequestParam UUID id) {
        UserResponse user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUser(
            @RequestParam UUID id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse updatedUser = userService.updateUser(id, userUpdateRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/users")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @RequestParam UUID id,
            @Valid @RequestBody UserPartialUpdateRequest partialUpdateRequest) {
        UserResponse updatedUser = userService.partialUpdateUser(id, partialUpdateRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@RequestParam UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
