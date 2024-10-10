package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Carrera;

public interface ICarreraDao extends JpaRepository<Carrera, Long> {
    
    Carrera findByCarrera(String carrera);
}
