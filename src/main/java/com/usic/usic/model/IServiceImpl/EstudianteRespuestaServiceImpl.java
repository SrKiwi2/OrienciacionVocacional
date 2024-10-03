package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.EstudianteRespuesta;
import com.usic.usic.model.Service.IEstudianteRespuestaService;
import com.usic.usic.model.dao.IEstudianteRespuestaDao;

@Service
public class EstudianteRespuestaServiceImpl implements IEstudianteRespuestaService{

    @Autowired
    private IEstudianteRespuestaDao estudianteRespuestaDao;

    @Override
    public List<EstudianteRespuesta> findAll() {
        // TODO Auto-generated method stub
        return estudianteRespuestaDao.findAll();
    }
    @Override
    public EstudianteRespuesta findById(Long idEntidad) {
        // TODO Auto-generated method stub
        return estudianteRespuestaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public EstudianteRespuesta save(EstudianteRespuesta entidad) {
        // TODO Auto-generated method stub
        return estudianteRespuestaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        // TODO Auto-generated method stub
        estudianteRespuestaDao.deleteById(idEntidad);
    }

    @Override
    public int countRespuestasSiByEstudiante(Long idEstudiante) {
        return estudianteRespuestaDao.countRespuestasSiByEstudiante(idEstudiante);
    }
    @Override
    public List<Object[]> findPreguntasYRespuestasConSI(Long idEstudiante) {
        return estudianteRespuestaDao.findPreguntasYRespuestasConSI(idEstudiante);
    }
    
    

}
