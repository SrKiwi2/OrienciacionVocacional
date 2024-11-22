package com.usic.usic.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Persona;

public interface IPersonaDao extends JpaRepository <Persona, Long>{

    @Query(value = "SELECT * FROM Persona p WHERE p.ci = :ci AND p._estado = 'E' LIMIT 1", nativeQuery = true)
    Persona validarCI(@Param("ci") String ci);

    Persona findByCorreo(String correo);

    Optional<Persona> findByCi(String ci);
}
