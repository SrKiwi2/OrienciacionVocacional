package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.IService.IColegioService;
import com.usic.usic.model.dao.IColegioDao;
import com.usic.usic.model.entity.Colegio;

@Service
public class ColegioServiceImpl implements IColegioService{

    @Autowired
    private IColegioDao colegioDao;

    @Override
    public List<Colegio> findAll() {
        return colegioDao.findAll();
    }

    @Override
    public Colegio findById(Long idEntidad) {
        return colegioDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Colegio save(Colegio entidad) {
        return colegioDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        colegioDao.deleteById(idEntidad);
    }

    @Override
    public Colegio buscarColegio(String nombre) {
        return colegioDao.findBynombre(nombre);
    }
    
}