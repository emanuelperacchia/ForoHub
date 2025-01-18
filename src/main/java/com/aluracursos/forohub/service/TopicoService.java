package com.aluracursos.forohub.service;

import com.aluracursos.forohub.datos.ValidacionException;
import com.aluracursos.forohub.datos.cursos.CursoEntity;
import com.aluracursos.forohub.datos.topicos.*;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import com.aluracursos.forohub.infra.security.SecurityFilter;
import com.aluracursos.forohub.repository.CursoRepository;
import com.aluracursos.forohub.repository.TopicoRepository;
import com.aluracursos.forohub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private SecurityFilter securityFilter;


    public TopicoDetalleDTO registrarTopico(RegistroTopicoDTO dto, UsuarioEntity autor) {
        if (topicoRepository.existsByTituloAndMensaje(dto.titulo(), dto.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el título '" + dto.titulo() + "' y el mensaje especificado");
        }

        CursoEntity curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new ValidacionException("Curso no encontrado con ID: " + dto.cursoId()));

        TopicosEntity topico = new TopicosEntity();
        topico.setTitulo(dto.titulo());
        topico.setMensaje(dto.mensaje());
        topico.setCurso(curso);
        topico.setAutor(autor);
        topico.setStatus(Estado.ABIERTO);

        topicoRepository.save(topico);

        return new TopicoDetalleDTO(topico);
    }

    @Transactional
    public List<TopicoDetalleDTO> listarTopicos() {
        var topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(TopicoDetalleDTO::new)
                .toList();
    }
    @Transactional
    public List<TopicoDetalleDTO> listarPrimeros10Topicos() {
        var topicos = topicoRepository.findTop10ByOrderByFechaCreacionAsc();
        return topicos.stream()
                .map(TopicoDetalleDTO::new)
                .toList();
    }
    @Transactional
    public List<TopicoDetalleDTO> buscarPorCursoYAnio(String curso, int anio) {
        log.info("Buscando tópicos para curso: {}, año: {}", curso, anio);
        var topicos = topicoRepository.buscarPorCursoYAnio(curso, anio);
        return topicos.stream()
                .map(TopicoDetalleDTO::new)
                .toList();
    }
    @Transactional
    public Page<TopicoDetalleDTO> listarTopicosPaginados(Pageable pageable) {
        var topicos = topicoRepository.findAll(pageable);
        return topicos.map(TopicoDetalleDTO::new);
    }

    @Transactional
    public Optional<TopicosEntity> obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id);
    }
    @Transactional
    public TopicoDetalleDTO actualizarTopico(Long id, ActualizarTopicoDTO datos, UsuarioEntity usuarioAutenticado) {
        TopicosEntity topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        if (!topico.getAutor().getId().equals(usuarioAutenticado.getId())) {
            throw new ValidacionException("No tienes permiso para modificar este tópico");
        }

        if (!topico.getTitulo().equals(datos.getTitulo()) &&
                topicoRepository.existsByTitulo(datos.getTitulo())) {
            throw new ValidacionException("Ya existe un tópico con ese título");
        }

        topico.setTitulo(datos.getTitulo());
        topico.setMensaje(datos.getMensaje());
        topico.setStatus(datos.getEstado());

        TopicosEntity topicoActualizado = topicoRepository.save(topico);

        return new TopicoDetalleDTO(topicoActualizado);
    }
    @Transactional
    public void eliminarTopico(Long id, UsuarioEntity usuario) {
        TopicosEntity topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        if (!topico.getAutor().getId().equals(usuario.getId())) {
            throw new SecurityException("No autorizado para eliminar este tópico");
        }

        topicoRepository.delete(topico);
    }
   @Transactional
   public TopicoDetalleDTO obtenerTopicoDetalle(Long id) {
       TopicosEntity topico = topicoRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

       return new TopicoDetalleDTO(topico);
   }

    }

