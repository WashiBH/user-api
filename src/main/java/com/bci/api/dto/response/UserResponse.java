package com.bci.api.dto.response;

import com.bci.api.dto.PhoneDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String nombre;
    private String correo;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;
    private String token;
    private Boolean activo;
    private List<PhoneDto> telefonos;
}
