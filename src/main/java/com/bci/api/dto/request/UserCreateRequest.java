package com.bci.api.dto.request;

import com.bci.api.dto.PhoneDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserCreateRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El formato del correo no es válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número")
    private String contrasena;

    @Valid
    private List<PhoneDto> telefonos;
}
