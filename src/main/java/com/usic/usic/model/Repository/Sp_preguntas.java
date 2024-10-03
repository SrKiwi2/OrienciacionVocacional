package com.usic.usic.model.Repository;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Sp_preguntas {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> ObtenerRespuestasrespondidas(Long id_estudiante, Long id_tipo_test) {
        String sql = "select p.id_pregunta, ('Pregunta: ' || p.id_pregunta) as descrip_preg from estudiante_respuesta er inner join respuesta r on r.id_respuesta = er.id_respuesta inner join pregunta p on p.id_pregunta = r.id_pregunta inner join tipo_test tt on tt.id_tipo_test = p.id_tipo_test where er.id_estudiante = ? and tt.id_tipo_test = ? and er._estado  = 'ACTIVO' and r._estado = 'ACTIVO' and p._estado = 'ACTIVO' and tt._estado = 'ACTIVO' group by p.id_pregunta order by p.id_pregunta asc, descrip_preg;";
        Object[] params = new Object[] {id_estudiante, id_tipo_test};
    
        try {
            return jdbcTemplate.queryForList(sql, params);
        } catch (EmptyResultDataAccessException e) {
            
            return null;
        }
    }

}


