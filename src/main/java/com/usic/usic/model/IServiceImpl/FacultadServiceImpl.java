package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Facultad;
import com.usic.usic.model.Service.IFacultadService;
import com.usic.usic.model.dao.IFacultadDao;

@Service
public class FacultadServiceImpl implements IFacultadService {

    @Autowired
    private IFacultadDao facultadDao;

    @Override
    public List<Facultad> findAll() {
        return facultadDao.findAll();
    }

    @Override
    public Facultad findById(Long idEntidad) {
        return facultadDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Facultad save(Facultad entidad) {
        return facultadDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        facultadDao.deleteById(idEntidad);
    }

    @Override
    public List<Facultad> listarFacultades() {
        return facultadDao.listarFacultades();
    }

    @Override
    public Facultad buscarFacultadPorNombre(String facultad) {
        return facultadDao.buscarFacultadPorNombre(facultad);
    }
    
}
