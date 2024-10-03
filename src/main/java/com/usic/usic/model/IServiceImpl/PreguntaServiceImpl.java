package com.usic.usic.model.IServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.dao.IPreguntaDao;

@Service
public class PreguntaServiceImpl implements IPreguntaService{

    @Autowired
    private IPreguntaDao preguntaDao;

    @Override
    public List<Pregunta> findAll() {
        // TODO Auto-generated method stub
        return preguntaDao.findAll(); //
    }

    @Override
    public Pregunta findById(Long idEntidad) {
        // TODO Auto-generated method stub
        return preguntaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Pregunta save(Pregunta entidad) {
        // TODO Auto-generated method stub
        return preguntaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        // TODO Auto-generated method stub
        preguntaDao.deleteById(idEntidad); //
    }

    @Override
    public Long findMaxRespuestaOrMinPregunta(Long idEstudiante, Long id_tipo_test) {
        return preguntaDao.findMinPreguntaNotInRespuestas(idEstudiante, id_tipo_test);
    }

    @Override
    public Long countByTipoTest(Long idTipoTest) {
        return preguntaDao.countByTipoTest(idTipoTest);
    }
    
}
