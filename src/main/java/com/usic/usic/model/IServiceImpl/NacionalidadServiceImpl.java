package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Nacionalidad;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.dao.INacionalidadDao;

@Service
public class NacionalidadServiceImpl implements INacionalidadService {

    @Autowired
    private INacionalidadDao nacionalidadDao;

    @Override
    public List<Nacionalidad> findAll() {
       return nacionalidadDao.findAll();
    }

    @Override
    public Nacionalidad findById(Long idEntidad) {
        return nacionalidadDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Nacionalidad save(Nacionalidad entidad) {
        return nacionalidadDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        nacionalidadDao.deleteById(idEntidad);
    }

    @Override
    public Nacionalidad buscarNacionalidad(String nombreNacionalidad) {
        return nacionalidadDao.findByNombreNacionalidad(nombreNacionalidad);
    }
    
}
