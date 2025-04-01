package com.bci.api.service;

import com.bci.api.dto.request.AuthRequest;
import com.bci.api.dto.response.AuthResponse;

public interface AuthenticationService {
    public AuthResponse signIn(AuthRequest authRequest);
}
