package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.datos.topicos.TopicosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<TopicosEntity, Long> {
}
