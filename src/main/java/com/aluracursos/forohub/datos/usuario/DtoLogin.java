package com.aluracursos.forohub.datos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record DtoLogin
        (
                @Email(message = "El correo electrónico debe ser válido")
                @NotBlank(message = "El correo electrónico no puede estar vacío")
                String correoElectronico,
                @NotBlank(message = "La contraseña no puede estar vacía")
                String contrasena
        ) {

    public String getContrasena() {
        return this.contrasena;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

}
