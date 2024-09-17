package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Estudiante;

public interface IEstudianteDao extends JpaRepository<Estudiante, Long>{
 
    @Query("SELECT e FROM Estudiante e WHERE e.persona.idPersona = :idPersona")
    Estudiante findByPersonaId(@Param("idPersona") Long idPersona);
}
