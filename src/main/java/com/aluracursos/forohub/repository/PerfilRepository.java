package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.datos.perfil.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
}
