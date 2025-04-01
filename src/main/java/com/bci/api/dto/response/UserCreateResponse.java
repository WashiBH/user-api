package com.bci.api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserCreateResponse {
    private UUID id;
    private String nombre;
    private String correo;
    private LocalDateTime creado;
    private LocalDateTime ultimoLogin;
    private String token;
    private Boolean activo;
}
