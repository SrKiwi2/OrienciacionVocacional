package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Service.IResultadoIaService;
import com.usic.usic.model.dao.IResultadoIaDao;

@Service
public class ResultadoIaServiceImpl implements IResultadoIaService {

    @Autowired
    private IResultadoIaDao resultadoIaDao;

    @Override
    public List<ResultadoIA> findAll() {
       return resultadoIaDao.findAll();
    }

    @Override
    public ResultadoIA findById(Long idEntidad) {
        return resultadoIaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public ResultadoIA save(ResultadoIA entidad) {
        return resultadoIaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        resultadoIaDao.deleteById(idEntidad);
    }
    
}