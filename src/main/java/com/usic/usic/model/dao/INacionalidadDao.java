package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Nacionalidad;

public interface INacionalidadDao extends JpaRepository<Nacionalidad, Long> {
    Nacionalidad findByNombreNacionalidad(String nombreNacionalidad);
}
