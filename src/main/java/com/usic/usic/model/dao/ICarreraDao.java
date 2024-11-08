package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.usic.usic.model.Entity.Carrera;

public interface ICarreraDao extends JpaRepository<Carrera, Long> {
    
    Carrera findByCarrera(String carrera);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO informe_carrera (id_informe_psicopedagoga, id_carrera) VALUES (?1, ?2)", nativeQuery = true)
    void insertInformeCarrera(Long idInformePsicopedagoga, Long idCarrera);
}
