package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.EstudianteRespuesta;

public interface IEstudianteRespuestaDao extends JpaRepository<EstudianteRespuesta, Long>{
    @Query("SELECT COUNT(er) FROM EstudianteRespuesta er JOIN er.respuesta r WHERE er.estudiante.id = :idEstudiante AND r.respuesta = 'SI'")
    int countRespuestasSiByEstudiante(@Param("idEstudiante") Long idEstudiante);

    @Query("SELECT p.pregunta, r.respuesta FROM EstudianteRespuesta er " +
           "JOIN er.respuesta r " +
           "JOIN r.pregunta p " +
           "WHERE er.estudiante.idEstudiante = :idEstudiante " +
           "AND r.respuesta = 'SI'")
    List<Object[]> findPreguntasYRespuestasConSI(@Param("idEstudiante") Long idEstudiante);

    // @Query("SELECT er.idEstudiante FROM EstudianteRespuesta er " +
    //        "INNER JOIN er.respuesta r " +
    //        "INNER JOIN r.pregunta p " +
    //        "INNER JOIN p.tipoTest tt " +
    //        "WHERE tt.idTipoTest = :idTipoTest " +
    //        "GROUP BY er.idEstudiante " +
    //        "HAVING COUNT(DISTINCT er.idRespuesta) >= 98")
    // List<Long> findEstudiantesConRespuestas(@Param("idTipoTest") Long idTipoTest);
}
