package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Carrera;
import com.usic.usic.model.Service.ICarreraService;
import com.usic.usic.model.dao.ICarreraDao;

@Service
public class ICarreraServiceImpl implements ICarreraService {

    @Autowired
    private ICarreraDao carreraDao;

    @Override
    public List<Carrera> findAll() {
        return carreraDao.findAll();
    }

    @Override
    public Carrera findById(Long idEntidad) {
        return carreraDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Carrera save(Carrera entidad) {
        return carreraDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        carreraDao.deleteById(idEntidad);
    }

    @Override
    public Carrera findByCarrera(String carrera) {
        return carreraDao.findByCarrera(carrera);
    }

    @Override
    public void insertInformeCarrera(Long idInformePsicopedagoga, Long idCarrera) {
        // TODO Auto-generated method stub
        carreraDao.insertInformeCarrera(idInformePsicopedagoga, idCarrera);
    }
    
}
