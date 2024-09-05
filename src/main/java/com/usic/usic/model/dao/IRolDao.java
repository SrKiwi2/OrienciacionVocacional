package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.entity.Rol;

public interface IRolDao extends JpaRepository <Rol, Long>{
    
    Rol findByNombre (String nombre);
}
