package com.aluracursos.forohub.infra.security;

import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServis {

    @Value("${api.security.secret}")
    private String apiSecret;

    private long expiration;

    public String generarToken(UsuarioEntity usuario) {
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(generarFechaExpiracion())
                .withIssuer("foroHub")
                .sign(algorithm);
    }

    public String getSubject(String token) {
        if (token == null || token.isBlank()) {
            throw new SecurityException("Token no proporcionado");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.require(algorithm)
                    .withIssuer("foroHub")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new SecurityException("Token JWT inválido o expirado", exception);
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}