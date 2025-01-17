package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.datos.usuario.DtoLogin;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import com.aluracursos.forohub.infra.security.TokenServis;
import com.aluracursos.forohub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenServis tokenService;

    @PostMapping
    public ResponseEntity<String> autenticacionUsuario(@RequestBody @Valid DtoLogin loginDto) {
        try {
            UsuarioEntity usuario = usuarioService.buscarPorEmail(loginDto.getCorreoElectronico());

            if (!passwordEncoder.matches(loginDto.getContrasena(), usuario.getContrasena())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
            String token = tokenService.generarToken(usuario);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
        }
    }
}
