package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Facultad;

public interface IFacultadDao extends JpaRepository<Facultad, Long> {
    
}
