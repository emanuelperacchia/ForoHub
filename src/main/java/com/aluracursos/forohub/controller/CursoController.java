package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.datos.cursos.CursoDTO;
import com.aluracursos.forohub.service.CursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<CursoDTO> crearCurso(@RequestBody @Valid CursoDTO cursoDTO){
        CursoDTO cursoCreado = cursoService.crearCurso(cursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCreado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerCursoPorId(@PathVariable Long id){
        CursoDTO curso = cursoService.obtenerCursoPorId(id);
        return ResponseEntity.ok(curso);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoDTO cursoDTO) {
        CursoDTO cursoActualizado = cursoService.actualizarCurso(id, cursoDTO);
        return ResponseEntity.ok(cursoActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<CursoDTO>> listarCursos(@RequestParam(defaultValue = "0", required = true) int pagina) {
        Page<CursoDTO> cursos = cursoService.listarCursos(pagina);
        return ResponseEntity.ok(cursos);
    }
}
