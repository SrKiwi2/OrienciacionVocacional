package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.ResultadoIA;

public interface IResultadoIaDao extends JpaRepository<ResultadoIA, Long>{

    List<ResultadoIA> findByEstudiante(Estudiante estudiante);
    
}
