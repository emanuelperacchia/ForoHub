package com.aluracursos.forohub.datos.usuario;

import com.aluracursos.forohub.datos.perfil.TipoPerfil;
import com.aluracursos.forohub.datos.respuesta.RespuestaEntity;
import com.aluracursos.forohub.datos.topicos.TopicosEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Table(name="usuarios")
@Entity(name="usuario")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class UsuarioEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long id;
    @Column(name="nombre_usuario", nullable = false, length = 100)
    @NonNull
    private String nombre;
    @Column(name="correo_electronico", nullable = false, unique = true, length = 150)
    @NotNull
    @Email
    private String correoElectronico;
    @Getter
    @NotNull
    @Column(name="contrasena", nullable = false, length = 255)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private TipoPerfil perfil;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicosEntity> topicos;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaEntity> respuestas;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public UsuarioEntity(Long id, String nombre, String correoElectronico, String contrasena, TipoPerfil perfil) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contrasena = PASSWORD_ENCODER.encode(contrasena);
        this.perfil = perfil;
    }

}


