package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.ColegioInterior;

public interface IColegioInteriorDao extends JpaRepository<ColegioInterior, Long>{
    
}
