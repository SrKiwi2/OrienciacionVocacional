package com.usic.usic.model.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
                        "where e._estado != 'INHABILITADO' and p._estado != 'X'";

        try {
            // Se pasa el parámetro id_tipo_test dos veces (uno para cada ? en la consulta)
            return jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        } catch (DataAccessException e) {
            // Manejar la excepción si es necesario
            return null;
        }
    }

    public Map<String, Object> ObtenerInterpretaciones(Long v_id_informe_psicopedagoga) {
        String sql = "SELECT split_part(p.interpretacion, '/', 1) AS columna1, split_part(p.interpretacion, '/', 2) AS columna2, split_part(p.interpretacion, '/', 3) AS columna3, split_part(p.interpretacion, '/', 4) AS columna4 FROM informe_psicopedagoga p WHERE p.id_informe_psicopedagoga = ?";
        Object[] params = new Object[] {v_id_informe_psicopedagoga};
    
        try {
            return jdbcTemplate.queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            
            return null;
        }
    }

    public List<Map<String, Object>> ObtenerCarrerasXInforme(Long v_id_informe_psicopedagoga) {
        String sql = "select carrera.carrera, facultad.facultad " +
                        "from informe_carrera " +
                        "inner join carrera on carrera.id_carrera = informe_carrera.id_carrera " +
                        "inner join facultad on facultad.id_facultad = carrera.id_facultad " +
                        "where informe_carrera.id_informe_psicopedagoga = ?";
        Object[] params = new Object[] {v_id_informe_psicopedagoga};
    
        try {
            return jdbcTemplate.queryForList(sql, params);
        } catch (EmptyResultDataAccessException e) {
            
            return null;
        }
    }

}
