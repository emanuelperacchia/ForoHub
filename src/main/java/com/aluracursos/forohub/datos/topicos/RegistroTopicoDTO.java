package com.aluracursos.forohub.datos.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegistroTopicoDTO( @NotBlank(message = "El título es obligatorio")
                                 @Length(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
                                 String titulo,

                                 @NotBlank(message = "El mensaje es obligatorio")
                                 String mensaje,

                                 @NotNull(message = "El ID del curso es obligatorio")
                                 Long cursoId) {

}
