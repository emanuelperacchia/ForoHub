package com.aluracursos.forohub.datos.usuario;

import com.aluracursos.forohub.datos.perfil.Perfil;

public record Usuario(
        String nombre,
        String correoElectronico,
        String contrasena,
        Perfil perfiles) {
}
