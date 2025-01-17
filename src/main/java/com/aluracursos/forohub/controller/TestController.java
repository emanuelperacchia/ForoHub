package com.aluracursos.forohub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    public TestController() {
        log.info("TestController cargado correctamente.");
    }

    @GetMapping
    public ResponseEntity<String> test() {
        log.info("Endpoint test llamado");
        return ResponseEntity.ok("La aplicación está funcionando correctamente.");
    }
}

