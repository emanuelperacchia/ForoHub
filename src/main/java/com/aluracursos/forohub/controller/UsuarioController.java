package com.aluracursos.forohub.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @PostMapping
    public void registrarUsuario(){
        System.out.println("El usuario llega correctamente");
    }
}
