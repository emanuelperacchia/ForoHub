package com.aluracursos.forohub.service;

import com.aluracursos.forohub.datos.ValidacionException;
import com.aluracursos.forohub.datos.cursos.CursoDTO;
import com.aluracursos.forohub.datos.cursos.CursoEntity;
import com.aluracursos.forohub.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoDTO crearCurso(CursoDTO cursoDTO) {
        if (cursoRepository.existsByNombre(cursoDTO.nombre())) {
            throw new ValidacionException("Ya existe un curso con el nombre: " + cursoDTO.nombre());
        }

        CursoEntity curso = new CursoEntity();
        curso.setNombre(cursoDTO.nombre());
        curso.setCategoria(cursoDTO.categoria());

        CursoEntity cursoGuardado = cursoRepository.save(curso);
        return new CursoDTO(cursoGuardado);
    }

    public CursoDTO obtenerCursoPorId(Long id) {
        CursoEntity curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Curso no encontrado con el ID: " + id));
        return new CursoDTO(curso);
    }

    public CursoDTO actualizarCurso(Long id, CursoDTO cursoDTO) {
        CursoEntity curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Curso no encontrado con el ID: " + id));

        curso.setNombre(cursoDTO.nombre());
        curso.setCategoria(cursoDTO.categoria());

        CursoEntity cursoActualizado = cursoRepository.save(curso);
        return new CursoDTO(cursoActualizado);
    }

    public void eliminarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ValidacionException("Curso no encontrado con el ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    public Page<CursoDTO> listarCursos(int pagina) {
        Pageable paginacion = PageRequest.of(pagina, 3, Sort.by("nombre").ascending());
        return cursoRepository.findAll(paginacion).map(CursoDTO::new);
    }
}
