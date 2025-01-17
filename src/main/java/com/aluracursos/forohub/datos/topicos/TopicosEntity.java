package com.aluracursos.forohub.datos.topicos;

import com.aluracursos.forohub.datos.cursos.CursoEntity;
import com.aluracursos.forohub.datos.respuesta.RespuestaEntity;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "topico")
@Table(name = "topicos")
public class TopicosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_topico")
    private Long id;

    @Column(name = "titulo_topico", nullable = false, length = 200)
    private String titulo;

    @Column(name = "mensaje_topico", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Estado status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", nullable = false)
    private UsuarioEntity autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoEntity curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaEntity> respuestas;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public void setAutorUltimaModificacion(UsuarioEntity usuarioAutenticado) {
    }
}
