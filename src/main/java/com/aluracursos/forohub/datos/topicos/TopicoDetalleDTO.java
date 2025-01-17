package com.aluracursos.forohub.datos.topicos;

import com.aluracursos.forohub.datos.cursos.CursoDTO;
import com.aluracursos.forohub.datos.usuario.UsuarioDTO;
import java.time.LocalDateTime;

public record TopicoDetalleDTO(Long id,
                               String titulo,
                               String mensaje,
                               Estado status,
                               LocalDateTime fechaCreacion,
                               UsuarioDTO autor,
                               CursoDTO curso) {
    public TopicoDetalleDTO(TopicosEntity topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getFechaCreacion(),
                new UsuarioDTO(topico.getAutor()),
                new CursoDTO(topico.getCurso())
        );
    }

}
