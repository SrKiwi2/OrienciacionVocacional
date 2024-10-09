package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Pregunta;

public interface IPreguntaDao extends JpaRepository<Pregunta, Long>{
    @Query("SELECT COALESCE(MIN(p.idPregunta), 0) " +
       "FROM Pregunta p " +
       "WHERE p.idPregunta NOT IN (" +
       "SELECT p.idPregunta " +
       "FROM EstudianteRespuesta er " +
       "INNER JOIN er.respuesta r " +
       "INNER JOIN r.pregunta p " +
       "INNER JOIN p.tipoTest tt " +
       "WHERE er.estudiante.idEstudiante = :idEstudiante " +
       "AND tt.id_tipo_test = :id_tipo_test " +
       "GROUP BY p.idPregunta)")
    Long findMinPreguntaNotInRespuestas(@Param("idEstudiante") Long idEstudiante, @Param("id_tipo_test") Long id_tipo_test);

    @Query("SELECT COUNT(p) FROM Pregunta p WHERE p.tipoTest.id_tipo_test = :idTipoTest")
    Long countByTipoTest(@Param("idTipoTest") Long idTipoTest);

    Pregunta findByPregunta(String pregunta);
}
