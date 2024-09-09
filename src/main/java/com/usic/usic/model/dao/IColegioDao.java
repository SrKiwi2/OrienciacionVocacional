package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Colegio;

public interface IColegioDao extends JpaRepository<Colegio, Long>{
    
    Colegio findByNombreColegio (String nombreColegio);
}
