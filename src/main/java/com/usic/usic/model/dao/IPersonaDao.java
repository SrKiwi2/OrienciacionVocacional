package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.entity.Persona;

public interface IPersonaDao extends JpaRepository <Persona, Long>{
    
    @Query("SELECT p FROM Persona p WHERE p.ci = :ci")
    Persona validarCI(@Param("ci") String ci);
}
