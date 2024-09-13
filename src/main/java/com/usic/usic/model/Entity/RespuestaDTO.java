package com.usic.usic.model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespuestaDTO {
    private Long idPregunta;
    private Long IdEstudiante;
    private String respuesta;
}
