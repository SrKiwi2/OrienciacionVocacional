package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.TipoColegio;

public interface ITipoColegioDao extends JpaRepository<TipoColegio, Long>{
    
}
