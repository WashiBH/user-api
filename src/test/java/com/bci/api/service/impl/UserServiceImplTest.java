package com.bci.api.service.impl;

import com.bci.api.configuration.JwtTokenProvider;
import com.bci.api.dto.request.UserCreateRequest;
import com.bci.api.dto.response.UserCreateResponse;
import com.bci.api.model.UserModel;
import com.bci.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    private UserCreateRequest userCreateRequest;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userCreateRequest = new UserCreateRequest("Juan Perez", "juan@gmail.com", "12345", List.of());

        userModel = new UserModel();
        userModel.setEmail("Juan@gmail.com");
        userModel.setName("Juan Perez");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByEmail(userCreateRequest.getCorreo())).thenReturn(false);
        when(passwordEncoder.encode(userCreateRequest.getContrasena())).thenReturn("password");
        when(tokenProvider.generateToken(userModel.getEmail())).thenReturn("token");
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserCreateResponse response = userService.createUser(userCreateRequest);

        assertNotNull(response);
        assertEquals(userCreateRequest.getCorreo(), response.getCorreo());
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

}

