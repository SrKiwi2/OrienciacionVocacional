package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Rol;
import com.usic.usic.model.Service.IRolService;
import com.usic.usic.model.dao.IRolDao;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolDao rolDao;

    @Override
    public List<Rol> findAll() {
        return rolDao.findAll();
    }

    @Override
    public Rol findById(Long idEntidad) {
        return rolDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Rol save(Rol entidad) {
        return rolDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        rolDao.deleteById(idEntidad);
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        return rolDao.findByNombre(nombre);
    }
    
}
