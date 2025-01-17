package com.aluracursos.forohub.datos.cursos;

public record CursoDTO(String nombre, Categoria categoria) {
    public CursoDTO(CursoEntity curso) {
       this(
               curso.getNombre(),
               curso.getCategoria()
       );
    }
}
