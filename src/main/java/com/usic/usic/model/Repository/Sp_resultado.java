package com.usic.usic.model.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Sp_resultado {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer ObtenerNumeroEstudiantesTerminados(Long id_tipo_test) {
        String sql = "select count(aux.numero_respuestas) " +
                    "from (" +
                    "  select count(distinct er.id_respuesta) as numero_respuestas " +
                    "  from estudiante_respuesta er " +
                    "  inner join respuesta r on r.id_respuesta = er.id_respuesta " +
                    "  inner join pregunta p on p.id_pregunta = r.id_pregunta " +
                    "  inner join tipo_test tt on tt.id_tipo_test = p.id_tipo_test " +
                    "  where tt.id_tipo_test = ? " +  // Placeholder para el parámetro id_tipo_test
                    "  group by er.id_estudiante, tt.id_tipo_test " +
                    "  having count(distinct er.id_respuesta) >= (" +
                    "    select count(distinct p.id_pregunta) " +
                    "    from pregunta p " +
                    "    inner join tipo_test tt on tt.id_tipo_test = p.id_tipo_test " +
                    "    where tt.id_tipo_test = ?" +  // Segundo uso del parámetro id_tipo_test
                    "  )" +
                    ") aux;";

        try {
            // Se pasa el parámetro id_tipo_test dos veces (uno para cada ? en la consulta)
            return jdbcTemplate.queryForObject(sql, new Object[]{id_tipo_test, id_tipo_test}, Integer.class);
        } catch (DataAccessException e) {
            // Manejar la excepción si es necesario
            return null;
        }
    }

    public Integer ObtenerEstudiantesTotales() {
        String sql = "select count(distinct e.id_estudiante) "+
                        "from estudiante e "+
                        "inner join persona p on p.id_persona = e.id_persona "+
                        "where e._estado != 'X' and p._estado != 'X'";

        try {
            // Se pasa el parámetro id_tipo_test dos veces (uno para cada ? en la consulta)
            return jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        } catch (DataAccessException e) {
            // Manejar la excepción si es necesario
            return null;
        }
    }

}
