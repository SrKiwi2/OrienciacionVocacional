package com.usic.usic.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.TipoTest;

public interface ITipoTestDao extends JpaRepository<TipoTest, Long> {
    TipoTest findByTipoTest(String tipoTest);

    @Query("SELECT t FROM TipoTest t WHERE :fechaActual BETWEEN t.fechaInicio AND t.fechaFin")
    List<TipoTest> findTestsHabilitados(@Param("fechaActual") LocalDate fechaActual);

}
