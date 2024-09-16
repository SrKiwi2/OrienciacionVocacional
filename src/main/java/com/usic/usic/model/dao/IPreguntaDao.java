package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Pregunta;

public interface IPreguntaDao extends JpaRepository<Pregunta, Long>{
    
}
