package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.ResultadoIA;

public interface IResultadoIaDao extends JpaRepository<ResultadoIA, Long>{

    List<ResultadoIA> findByEstudiante(Estudiante estudiante);

    @Query("SELECT r FROM ResultadoIA r WHERE r.estudiante = :estudiante AND r.tipoTest.id_tipo_test = :id_tipo_test")
    List<ResultadoIA> findByEstudianteAndTipoTest(@Param("estudiante") Estudiante estudiante, @Param("id_tipo_test") Long id_tipo_test);
    
}
