package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.dao.IEstudianteDao;

@Service
public class EstudianteServiceimpl implements IEstudianteService{

    @Autowired
    private IEstudianteDao estudianteDao; 

    @Override
    public List<Estudiante> findAll() {
       return estudianteDao.findAll();
    }

    @Override
    public Estudiante findById(Long idEntidad) {
        return estudianteDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Estudiante save(Estudiante entidad) {
        return estudianteDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        estudianteDao.deleteById(idEntidad);
    }

    @Override
    public Estudiante findByPersona(Persona persona) {
        return estudianteDao.findByPersonaId(persona.getIdPersona());
    }

    @Override
    public Colegio findColegioByIdEstudiante(Long idEstudiante) {
        return estudianteDao.findColegioByIdEstudiante(idEstudiante);
    }
    
}
