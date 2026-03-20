package com.project.mamoryJar.dto.request;

import com.project.mamoryJar.model.domain.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    @NotBlank // @NotBlank solo funciona con String
    private String nombre;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=10)
    private String contraseña; // Luego deberá encriptarse en el Service, nunca en el DTO.

    // valida que el valor exista
    @NotNull 
    private Rol rol;
}
