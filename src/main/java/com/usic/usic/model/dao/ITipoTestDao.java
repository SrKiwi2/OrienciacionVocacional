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

    @Query("SELECT COUNT(DISTINCT p.idPregunta) " +
       "FROM Pregunta p " +
       "INNER JOIN p.tipoTest tt " +
       "WHERE tt.id_tipo_test = :id_tipo_test " +
       "AND p.idPregunta NOT IN ( " +
       "   SELECT p.idPregunta " +
       "   FROM EstudianteRespuesta er " +
       "   INNER JOIN er.respuesta r " +
       "   INNER JOIN r.pregunta p " +
       "   INNER JOIN p.tipoTest tt " +
       "   WHERE er.estudiante.idEstudiante = :idEstudiante " +
       "   AND tt.id_tipo_test = :id_tipo_test " +
       "   AND er.estado = 'ACTIVO' " +
       "   AND r.estado = 'ACTIVO' " +
       "   AND p.estado = 'ACTIVO' " +
       "   AND tt.estado = 'ACTIVO' " +
       "   GROUP BY p.idPregunta) " )
    Long countDistinctPreguntasNotRespondidas(@Param("id_tipo_test") Long id_tipo_test, @Param("idEstudiante") Long idEstudiante);

}
