package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.datos.ValidacionException;
import com.aluracursos.forohub.datos.cursos.CursoEntity;
import com.aluracursos.forohub.datos.topicos.*;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import com.aluracursos.forohub.repository.CursoRepository;
import com.aluracursos.forohub.repository.TopicoRepository;
import com.aluracursos.forohub.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
@Validated
@Slf4j
@SecurityRequirement(name = "bearer-key")
public class TopicosController {

    @Autowired
    private TopicoService topicoService;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDetalleDTO> crearTopico(
            @RequestBody @Valid RegistroTopicoDTO datos,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        if (usuarioAutenticado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        var topico = topicoService.registrarTopico(datos, usuarioAutenticado);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }


    @GetMapping
    public ResponseEntity<List<TopicoDetalleDTO>> listarTopicos() {
        var topicos = topicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/primeros-10")
    public ResponseEntity<List<TopicoDetalleDTO>> listarPrimeros10() {
        var topicos = topicoService.listarPrimeros10Topicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<TopicoDetalleDTO>> filtrarPorCursoYAnio(
            @RequestParam String curso,
            @RequestParam int anio) {
        log.info("Parámetros recibidos - curso: '{}', año: {}", curso, anio);

        // Verificar si el curso existe
        var cursos = cursoRepository.findByNombre(curso);
        if (cursos.isEmpty()) {
            log.warn("No se encontró ningún curso con el nombre '{}'", curso);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado");
        }
        CursoEntity cursoExistente = cursos.get(0); // Tomar el primer curso si hay duplicados
        log.info("Curso encontrado: {}", cursoExistente.getNombre());

        // Calcular el rango de fechas para el año dado
        LocalDateTime inicioAnio = LocalDateTime.of(anio, 1, 1, 0, 0);
        LocalDateTime finAnio = LocalDateTime.of(anio, 12, 31, 23, 59);

        // Llamar al servicio para buscar los tópicos
        var topicos = topicoService.buscarPorCursoYRangoFechas(curso, inicioAnio, finAnio);
        if (topicos.isEmpty()) {
            log.info("No se encontraron tópicos para el curso '{}' en el año {}", curso, anio);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("No se encontraron tópicos para el curso '%s' en el año %d", curso, anio));
        }

        log.info("Tópicos encontrados: {}", topicos.size());
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<TopicoDetalleDTO>> listarPaginado(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable) {
        var topicos = topicoService.listarTopicosPaginados(pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalleDTO> obtenerTopico(@PathVariable Long id) {
        try {
            TopicoDetalleDTO topicoDetalle = topicoService.obtenerTopicoDetalle(id);

            return ResponseEntity.ok(topicoDetalle);
        } catch (IllegalArgumentException | EntityNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDetalleDTO> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid ActualizarTopicoDTO datos,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        if (usuarioAutenticado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        try {
            TopicoDetalleDTO topicoActualizado = topicoService.actualizarTopico(id, datos, usuarioAutenticado);
            return ResponseEntity.ok(topicoActualizado);
        } catch (ValidacionException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuarioAutenticado) {

        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            topicoService.eliminarTopico(id, usuarioAutenticado);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

