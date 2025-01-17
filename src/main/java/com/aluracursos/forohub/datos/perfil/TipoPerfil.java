package com.aluracursos.forohub.datos.perfil;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum TipoPerfil {
    @JsonAlias("Profesor")
    PROFESOR,
    @JsonAlias("Alumno")
    ALUMNO,
    @JsonAlias("Administracion")
    ADMINISTRACION
}
