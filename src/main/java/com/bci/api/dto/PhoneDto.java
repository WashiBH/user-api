package com.bci.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneDto {
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String numero;

    @NotBlank(message = "El código de ciudad no puede estar vacío")
    private String codigoCiudad;

    @NotBlank(message = "El código de país no puede estar vacío")
    private String codigoPais;
}
