package com.usic.usic.model.Service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.EstudianteRespuesta;

@Service
public interface IEstudianteRespuestaService extends IServiceGenerico<EstudianteRespuesta, Long>{
    int countRespuestasSiByEstudiante(@Param("idEstudiante") Long idEstudiante);

    List<Object[]> findPreguntasYRespuestasConSI(@Param("idEstudiante") Long idEstudiante);
}
