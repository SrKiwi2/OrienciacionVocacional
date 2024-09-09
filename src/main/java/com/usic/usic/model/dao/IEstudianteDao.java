package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Estudiante;

public interface IEstudianteDao extends JpaRepository<Estudiante, Long>{
    
}
