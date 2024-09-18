package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.ColegioInterior;
import com.usic.usic.model.Service.IColegioInteriorService;
import com.usic.usic.model.dao.IColegioInteriorDao;

@Service
public class ColegioInteriorServiceImpl implements IColegioInteriorService{

    @Autowired
    private IColegioInteriorDao colegioInteriorDao;
    @Override
    public List<ColegioInterior> findAll() {
        // TODO Auto-generated method stub
        return colegioInteriorDao.findAll(); 
    }

    @Override
    public ColegioInterior findById(Long idEntidad) {
        // TODO Auto-generated method stub
        return colegioInteriorDao.findById(idEntidad).orElse(null);
    }

    @Override
    public ColegioInterior save(ColegioInterior entidad) {
        // TODO Auto-generated method stub
        return colegioInteriorDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        // TODO Auto-generated method stub
        colegioInteriorDao.deleteById(idEntidad);
    }
    
}
