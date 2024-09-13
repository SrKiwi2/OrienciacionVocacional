package com.usic.usic.model.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Respuesta;

@Service
public interface IRespuestaService extends IServiceGenerico<Respuesta, Long>{
    Respuesta guardarRespuesta(Respuesta respuesta);
    List<Respuesta> obtenerRespuestaPorEstudiante(Estudiante estudiante);
}
