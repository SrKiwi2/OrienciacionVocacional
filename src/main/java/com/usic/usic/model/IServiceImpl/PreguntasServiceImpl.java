package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.dao.IPreguntaDao;

@Service
public class PreguntasServiceImpl implements IPreguntaService{
    
    @Autowired
    private IPreguntaDao preguntaDao;

    @Override
    public List<Pregunta> findAll() {
       return preguntaDao.findAll();
    }

    @Override
    public Pregunta findById(Long idEntidad) {
        return preguntaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Pregunta save(Pregunta entidad) {
        return preguntaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        preguntaDao.deleteById(idEntidad);
    }
}
