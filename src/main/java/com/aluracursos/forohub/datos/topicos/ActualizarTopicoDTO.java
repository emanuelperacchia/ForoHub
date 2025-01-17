package com.aluracursos.forohub.datos.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
public class ActualizarTopicoDTO {

        @NotBlank(message = "El título no puede estar vacío")
        @Length(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
        private String titulo;

        @NotBlank(message = "El mensaje no puede estar vacío")
        private String mensaje;

        @NotNull(message = "El estado no puede ser nulo")
        private Estado estado;
    }
