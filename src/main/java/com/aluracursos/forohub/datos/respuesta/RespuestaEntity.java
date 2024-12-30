package com.aluracursos.forohub.datos.respuesta;

import com.aluracursos.forohub.datos.topicos.TopicosEntity;
import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity(name="respuesta")
@Table(name="respuestas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RespuestaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_respuesta")
    private Long id;

    @Column(name = "mensaje_respuesta", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private TopicosEntity topico;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private UsuarioEntity autor;

    @Column(name = "solucion", nullable = false)
    private boolean solucion = false;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
