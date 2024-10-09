package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.TipoPregunta;
import com.usic.usic.model.Service.ITipoPreguntaService;
import com.usic.usic.model.dao.ITipoPreguntaDao;

@Service
public class TipoPreguntaServiceImpl implements ITipoPreguntaService{

    @Autowired
    private ITipoPreguntaDao tipoPreguntaDao;

    @Override
    public List<TipoPregunta> findAll() {
        return tipoPreguntaDao.findAll();
    }

    @Override
    public TipoPregunta findById(Long idEntidad) {
        return tipoPreguntaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public TipoPregunta save(TipoPregunta entidad) {
        return tipoPreguntaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        tipoPreguntaDao.deleteById(idEntidad);
    }

    @Override
    public List<TipoPregunta> getTipoPreguntaByRespuestaId(Long idRespuesta) {
        return tipoPreguntaDao.findTipoPreguntaByRespuestaId(idRespuesta);
    }

    @Override
    public TipoPregunta findByTipoPregunta(String tipoPregunta) {
        return tipoPreguntaDao.findByTipoPregunta(tipoPregunta);
    }
    
}
