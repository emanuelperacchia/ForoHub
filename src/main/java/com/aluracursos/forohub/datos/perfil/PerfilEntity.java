package com.aluracursos.forohub.datos.perfil;

import com.aluracursos.forohub.datos.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity(name="perfil")
@Table(name = "perfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PerfilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPerfil nombre;

    @ManyToMany(mappedBy = "perfiles")
    private Set<UsuarioEntity> usuarios;
}
