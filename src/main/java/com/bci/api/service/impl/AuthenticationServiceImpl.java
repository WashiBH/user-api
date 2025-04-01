package com.bci.api.service.impl;

import com.bci.api.configuration.JwtTokenProvider;
import com.bci.api.dto.request.AuthRequest;
import com.bci.api.dto.response.AuthResponse;
import com.bci.api.model.UserModel;
import com.bci.api.repository.UserRepository;
import com.bci.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signIn(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        log.info("authentication.getName(): {}", authentication.getName());

        UserModel user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + authRequest.getUsername()));

        user.setLastLogin(LocalDateTime.now());
        user.setToken(tokenProvider.generateToken(authentication.getName()));
        userRepository.save(user);

        return new AuthResponse(user.getToken());
    }
}
