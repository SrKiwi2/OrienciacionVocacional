package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.TipoColegio;
import com.usic.usic.model.Service.ITipoColegioService;
import com.usic.usic.model.dao.ITipoColegioDao;

@Service
public class TipoColegioServiceImpl implements ITipoColegioService{

    @Autowired
    private ITipoColegioDao tipoColegioDao;

    @Override
    public List<TipoColegio> findAll() {
        return tipoColegioDao.findAll();
    }

    @Override
    public TipoColegio findById(Long idEntidad) {
       return tipoColegioDao.findById(idEntidad).orElse(null);
    }

    @Override
    public TipoColegio save(TipoColegio entidad) {
       return tipoColegioDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        tipoColegioDao.deleteById(idEntidad);
    }
    
}
