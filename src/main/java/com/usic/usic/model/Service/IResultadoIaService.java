package com.usic.usic.model.Service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.ResultadoIA;

@Service
public interface IResultadoIaService extends IServiceGenerico<ResultadoIA, Long>{

    List<ResultadoIA> findByEstudiante(Estudiante estudiante);

    List<ResultadoIA> findByEstudianteAndTipoTest(@Param("estudiante") Estudiante estudiante, @Param("id_tipo_test") Long id_tipo_test);
    
}
