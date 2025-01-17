package com.aluracursos.forohub.datos.usuario;

import com.aluracursos.forohub.datos.perfil.TipoPerfil;

public record Usuario(
        String nombre,
        String correoElectronico,
        String contrasena,
        TipoPerfil perfil) {
    public Usuario(UsuarioDTO dto) {
        this(dto.nombre(), dto.correoElectronico(), dto.contrasena(), dto.perfil());
    }


}
