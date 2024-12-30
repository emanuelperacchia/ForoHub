package com.aluracursos.forohub.datos.usuario;

import com.aluracursos.forohub.datos.perfil.PerfilEntity;
import com.aluracursos.forohub.datos.respuesta.RespuestaEntity;
import com.aluracursos.forohub.datos.topicos.TopicosEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Table(name="usuarios")
@Entity(name="usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long id;
    @Column(name="nombre_usuario", nullable = false, length = 100)
    private String nombre;
    @Column(name="correo_electronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;
    @Column(name="contrasena", nullable = false, length = 255)
    private String contrasena;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<PerfilEntity> perfiles;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicosEntity> topicos;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaEntity> respuestas;

}
