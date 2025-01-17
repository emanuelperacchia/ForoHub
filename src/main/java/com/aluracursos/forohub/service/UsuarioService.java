package com.aluracursos.forohub.service;

import com.aluracursos.forohub.datos.perfil.TipoPerfil;
import com.aluracursos.forohub.datos.usuario.UsuarioDTO;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import com.aluracursos.forohub.infra.security.SecurityConfiguration;
import com.aluracursos.forohub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
   @Autowired
   private SecurityConfiguration securityConfiguration;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronico(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public UsuarioEntity buscarPorEmail(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public boolean existeCorreoElectronico(String correoElectronico) {
        return usuarioRepository.existsByCorreoElectronico(correoElectronico);
    }


    @Transactional
    public UsuarioEntity crearUsuario(UsuarioDTO usuario) {
        if (usuarioRepository.existsByCorreoElectronico(usuario.correoElectronico())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setNombre(usuario.nombre());
        nuevoUsuario.setCorreoElectronico(usuario.correoElectronico());
        nuevoUsuario.setContrasena(securityConfiguration.passwordEncoder().encode(usuario.contrasena()));
        nuevoUsuario.setPerfil(usuario.perfil());
        return usuarioRepository.save(nuevoUsuario);
    }
    public UsuarioEntity obtenerPorCorreo(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correoElectronico));
    }

    public UsuarioEntity obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ID: " + id));
    }
    @Transactional
    public Optional<UsuarioEntity> actualizarUsuario(Long id, String nombreUsuario, String correoElectronico, String contrasena, TipoPerfil perfil) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(nombreUsuario);
            usuario.setCorreoElectronico(correoElectronico);
            if (contrasena != null && !contrasena.isEmpty()) {
                usuario.setContrasena(securityConfiguration.passwordEncoder().encode(contrasena));
            }
            usuario.setPerfil(perfil);
            return usuarioRepository.save(usuario);
        });
    }
    public UsuarioEntity obtenerUsuarioPorCorreo(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correoElectronico));
    }
    @Transactional
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Iterable<UsuarioEntity> obtenerUsuariosPorPerfil(TipoPerfil perfil) {
        return usuarioRepository.findByPerfil(perfil);
    }
    public boolean validarCredenciales(String correoElectronico, String contrasena) {
        UsuarioEntity usuario = usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElse(null);
        if (usuario == null) {
            return false;         }
        return securityConfiguration.passwordEncoder().matches(contrasena, usuario.getContrasena());
    }

}