package com.usic.usic.model.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Pregunta;

@Service
public interface IPreguntaService extends IServiceGenerico <Pregunta, Long>{
    Long findMaxRespuestaOrMinPregunta(Long idEstudiante, Long id_tipo_test);
}
