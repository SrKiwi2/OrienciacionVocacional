package com.usic.usic.model.Service;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Pregunta;

@Service
public interface IPreguntaService extends IServiceGenerico <Pregunta, Long>{
    Long findMaxRespuestaOrMinPregunta(Long idEstudiante, Long id_tipo_test);

    Long countByTipoTest(@Param("idTipoTest") Long idTipoTest);

    Pregunta findByPregunta(String pregunta);
}
