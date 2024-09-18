package com.usic.usic.model.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usic.usic.model.Entity.TipoPregunta;

public interface ITipoPreguntaDao extends JpaRepository<TipoPregunta, Long>{
    
    @Query("select tp from Respuesta r " +
       "inner join r.pregunta p " +
       "inner join p.tipoPregunta tp " +
       "where r.id = :idRespuesta " +
       "and r.estado = 'ACTIVO' " +
       "and p.estado = 'ACTIVO' " +
       "and tp.estado = 'ACTIVO'")
    List<TipoPregunta> findTipoPreguntaByRespuestaId(@Param("idRespuesta") Long idRespuesta);

}
