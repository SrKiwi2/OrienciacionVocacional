package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Genero;

public interface IGeneroDao extends JpaRepository<Genero, Long> {
    
    Genero findByGenero(String genero);
}
