package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.datos.cursos.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long> {
    boolean existsByNombre(String nombre);

    List<CursoEntity> findByNombre(String nombre);
}
