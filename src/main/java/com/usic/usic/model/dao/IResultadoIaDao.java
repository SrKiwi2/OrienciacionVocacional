package com.usic.usic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.ResultadoIA;

public interface IResultadoIaDao extends JpaRepository<ResultadoIA, Long>{

    List<ResultadoIA> findByEstudiante(Estudiante estudiante);

    @Query("SELECT r FROM ResultadoIA r WHERE r.estudiante = :estudiante AND r.tipoTest.id_tipo_test = :id_tipo_test")
    List<ResultadoIA> findByEstudianteAndTipoTest(@Param("estudiante") Estudiante estudiante, @Param("id_tipo_test") Long id_tipo_test);

    @Query(value = "SELECT split_part(p.interpretacion, '/', 1) AS columna1, " +
                   "split_part(p.interpretacion, '/', 2) AS columna2, " +
                   "split_part(p.interpretacion, '/', 3) AS columna3 " +
                   "FROM informe_psicopedagoga p " +
                   "WHERE p.id_informe_psicopedagoga = :idInforme",
           nativeQuery = true)
    List<InterpretacionProjection> findInterpretacionByIdInforme(@Param("idInforme") Long idInforme);
    
    public interface InterpretacionProjection {
        String getColumna1();
        String getColumna2();
        String getColumna3();
    }
}
