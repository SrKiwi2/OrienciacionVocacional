package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;

public interface IEstudianteDao extends JpaRepository<Estudiante, Long>{
 
    @Query("SELECT e FROM Estudiante e WHERE e.persona.idPersona = :idPersona")
    Estudiante findByPersonaId(@Param("idPersona") Long idPersona);

    @Query("SELECT e.colegio FROM Estudiante e WHERE e.idEstudiante = :idEstudiante")
    Colegio findColegioByIdEstudiante(@Param("idEstudiante") Long idEstudiante);

    @Query("SELECT e FROM Estudiante e WHERE e.estado NOT IN ('INHABILITADO', 'X') ORDER BY e.idEstudiante DESC")
    List<Estudiante> findAllOrdered();

    @Query("SELECT CASE WHEN COUNT(er) > 0 THEN 'SI' ELSE 'NO' END " +
       "FROM EstudianteRespuesta er " +
       "JOIN er.respuesta r " +
       "JOIN r.pregunta p " +
       "JOIN p.tipoTest tt " +
       "WHERE er.estudiante.idEstudiante = :idEstudiante " +
       "AND tt.tipoTest = 'Chaside'")
    String hasCompletedChasideTest(@Param("idEstudiante") Long idEstudiante);

}
