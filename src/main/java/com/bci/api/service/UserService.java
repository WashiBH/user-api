package com.bci.api.service;

import com.bci.api.dto.request.UserCreateRequest;
import com.bci.api.dto.request.UserPartialUpdateRequest;
import com.bci.api.dto.request.UserUpdateRequest;
import com.bci.api.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserCreateRequest userRequest);
    UserResponse getUserById(UUID id);
    UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest);
    UserResponse partialUpdateUser(UUID id, UserPartialUpdateRequest partialUpdateRequest);
    void deleteUser(UUID id);
    List<UserResponse> getAllUsers();
}
