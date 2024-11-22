package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.usic.usic.model.Entity.Facultad;

public interface IFacultadDao extends JpaRepository<Facultad, Long> {
    
    @Query("SELECT f FROM Facultad f WHERE f.estado = 'ACTIVO'")
    List<Facultad> listarFacultades();

    @Query("SELECT f FROM Facultad f WHERE f.facultad = ?1 AND f.estado = 'ACTIVO'")
    Facultad buscarFacultadPorNombre(String facultad);

}
