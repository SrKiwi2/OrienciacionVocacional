package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.entity.Sexo;

public interface ISexoDao extends JpaRepository<Sexo, Long> {
    
    Sexo findByNombreSexo(String nombreSexo);
}
