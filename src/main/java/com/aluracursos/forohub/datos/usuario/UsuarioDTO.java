package com.aluracursos.forohub.datos.usuario;

import com.aluracursos.forohub.datos.perfil.TipoPerfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,
        @Email(message = "El correo electrónico no es válido")
        String correoElectronico,
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena,
        TipoPerfil perfil) {
    public UsuarioDTO(UsuarioEntity usuario) {
        this(
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getContrasena(),
                usuario.getPerfil()
        );
    }
}
