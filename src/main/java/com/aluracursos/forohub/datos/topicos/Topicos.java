package com.aluracursos.forohub.datos.topicos;

import com.aluracursos.forohub.datos.cursos.Curso;
import com.aluracursos.forohub.datos.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

public record Topicos(
      @NotBlank String titulo,
      @NotBlank String mensaje,
        Usuario autor,
        Curso curso
        ) {}

