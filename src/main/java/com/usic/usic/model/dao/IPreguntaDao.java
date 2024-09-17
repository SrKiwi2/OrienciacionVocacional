package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Pregunta;

public interface IPreguntaDao extends JpaRepository<Pregunta, Long>{

    @Query("SELECT COALESCE(MAX(r.idRespuesta), (SELECT MIN(p2.idPregunta) FROM Pregunta p2 WHERE p2.estado = 'ACTIVO')) " +
       "FROM EstudianteRespuesta er " +
       "JOIN er.respuesta r " +
       "JOIN r.pregunta p " +
       "JOIN p.tipoTest tt " +
       "WHERE er.estudiante.idEstudiante = :idEstudiante AND tt.id_tipo_test = :id_tipo_test " +
       "AND r.estado = 'ACTIVO' AND p.estado = 'ACTIVO' AND tt.estado = 'ACTIVO'")
    Long findMaxRespuestaOrMinPregunta(@Param("idEstudiante") Long idEstudiante, @Param("id_tipo_test") Long id_tipo_test);

}
