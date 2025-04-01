package com.bci.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
