package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.datos.ValidacionException;
import com.aluracursos.forohub.datos.usuario.UsuarioDTO;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import com.aluracursos.forohub.infra.security.TokenServis;
import com.aluracursos.forohub.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/signup")
@Slf4j
public class UsuarioController {

    @Autowired
    private TokenServis tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioDTO usuario) {
        try {
            UsuarioEntity nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Usuario creado con éxito" + nuevoUsuario));
        } catch (ValidacionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno del servidor"));
        }
    }
}