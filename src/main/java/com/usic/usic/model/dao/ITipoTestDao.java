package com.usic.usic.model.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.TipoTest;

public interface ITipoTestDao extends JpaRepository<TipoTest, Long> {
    TipoTest findByTipoTest(String tipoTest);

    @Query("SELECT t FROM TipoTest t WHERE :fechaActual BETWEEN t.fechaInicio AND t.fechaFin")
    List<TipoTest> findTestsHabilitados(@Param("fechaActual") LocalDate fechaActual);

    // @Query("SELECT tt.id_tipo_test, tt.tipoTest, 'REALIZADO' AS estado " +
    //    "FROM TipoTest tt " +
    //    "JOIN tt.preguntas p " +
    //    "JOIN p.respuestas r " +
    //    "JOIN r.estudiantesRespuestas er " +
    //    "WHERE er.estudiante.idEstudiante = :idEstudiante " +
    //    "GROUP BY tt.id_tipo_test, tt.tipoTest")
    //    List<Object[]> findTipoTestRealizado(@Param("idEstudiante") Long idEstudiante);
}
