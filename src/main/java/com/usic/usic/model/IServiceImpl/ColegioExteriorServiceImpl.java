package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.ColegioExterior;
import com.usic.usic.model.Service.IColegioExteriorService;
import com.usic.usic.model.dao.IColegioExteriorDao;

@Service
public class ColegioExteriorServiceImpl implements IColegioExteriorService{

    @Autowired
    private IColegioExteriorDao colegioExteriorDao;

    @Override
    public List<ColegioExterior> findAll() {
        // TODO Auto-generated method stub
        return colegioExteriorDao.findAll();    
    }

    @Override
    public ColegioExterior findById(Long idEntidad) {
        // TODO Auto-generated method stub
        return colegioExteriorDao.findById(idEntidad).orElse(null);
    }

    @Override
    public ColegioExterior save(ColegioExterior entidad) {
        // TODO Auto-generated method stub
       return colegioExteriorDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        // TODO Auto-generated method stub
        colegioExteriorDao.deleteById(idEntidad);
    }
    
}
