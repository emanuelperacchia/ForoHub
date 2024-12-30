package com.aluracursos.forohub.datos.topicos;

import com.aluracursos.forohub.datos.cursos.Curso;
import com.aluracursos.forohub.datos.respuesta.Respuesta;
import com.aluracursos.forohub.datos.usuario.Usuario;
import org.springframework.format.annotation.DateTimeFormat;

public record Topicos(
        String titulo,
        String mensaje,
        DateTimeFormat fechaCreacion,
        String status,
        Usuario autor,
        Curso curso,
        Respuesta respuestas
        ) {
}
