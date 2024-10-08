package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.dao.IPersonaDao;

@Service
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    private IPersonaDao personaDao;

    @Override
    public List<Persona> findAll() {
        return personaDao.findAll();
    }

    @Override
    public Persona findById(Long idEntidad) {
        return personaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Persona save(Persona entidad) {
        return personaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        personaDao.deleteById(idEntidad);
    }

    @Override
    public Persona validarCI(String ci) {
        return personaDao.validarCI(ci);
    }

    @Override
    public Persona findByCorreo(String correo) {
        return personaDao.findByCorreo(correo);
    }
}
