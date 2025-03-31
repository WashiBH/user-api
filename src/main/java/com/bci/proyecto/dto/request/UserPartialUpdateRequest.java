package com.bci.proyecto.dto.request;

import com.bci.proyecto.dto.PhoneDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserPartialUpdateRequest {
    private String nombre;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número")
    private String contrasena;

    @Valid
    private List<PhoneDto> telefonos;
}
