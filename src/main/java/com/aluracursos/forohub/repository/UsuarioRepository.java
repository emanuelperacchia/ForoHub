package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.datos.perfil.TipoPerfil;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Iterable<UsuarioEntity> findByPerfil(TipoPerfil perfil);

    Optional<UsuarioEntity> findByCorreoElectronico(String correoElectronico);

    @Query("SELECT u FROM usuario u WHERE u.correoElectronico = :correoElectronico AND u.contrasena = :contrasena")
    Optional<UsuarioEntity> findByEmailAndPassword(@Param("correoElectronico") String correoElectronico, @Param("contrasena") String contrasena);

    boolean existsByCorreoElectronico(String correoElectronico);

    Optional <UsuarioEntity> findByNombre(String nombre);
}