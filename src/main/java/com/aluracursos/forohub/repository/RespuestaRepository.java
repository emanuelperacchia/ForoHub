package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.datos.respuesta.RespuestaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<RespuestaEntity, Long> {
}
