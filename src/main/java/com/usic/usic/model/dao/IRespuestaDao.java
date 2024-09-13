package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Respuesta;

public interface IRespuestaDao extends JpaRepository<Respuesta, Long>{
    List<Respuesta> findByEstudiante(Estudiante estudiante);
}
