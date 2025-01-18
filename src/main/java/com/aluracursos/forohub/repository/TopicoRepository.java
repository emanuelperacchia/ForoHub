package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.datos.topicos.TopicosEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<TopicosEntity, Long> {

    boolean existsByTitulo(String titulo);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    List<TopicosEntity> findTop10ByOrderByFechaCreacionAsc();

    @Query("SELECT t FROM topico t WHERE t.curso.nombre = :curso " +
            "AND YEAR(t.fechaCreacion) = :anio")
    List<TopicosEntity> buscarPorCursoYAnio(@Param("curso") String curso,
                                            @Param("anio") int anio);
    Page<TopicosEntity> findAll(Pageable pageable);
}
