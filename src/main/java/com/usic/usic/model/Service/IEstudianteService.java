package com.usic.usic.model.Service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;

@Service
public interface IEstudianteService extends IServiceGenerico<Estudiante, Long>{
    Estudiante findByPersona(Persona persona);

    Colegio findColegioByIdEstudiante(@Param("idEstudiante") Long idEstudiante);

    List<Estudiante> findAllOrdered();

    String hasCompletedChasideTest(@Param("idEstudiante") Long idEstudiante);
}
