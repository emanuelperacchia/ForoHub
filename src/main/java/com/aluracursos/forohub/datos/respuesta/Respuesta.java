package com.aluracursos.forohub.datos.respuesta;

import com.aluracursos.forohub.datos.topicos.Topicos;
import com.aluracursos.forohub.datos.usuario.Usuario;
import org.springframework.format.annotation.DateTimeFormat;

public record Respuesta(String mensaje, Long idTopico,Long idAutor, boolean solucion) {
}
