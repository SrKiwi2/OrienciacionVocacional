package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Respuesta;
import com.usic.usic.model.Service.IRespuestaService;
import com.usic.usic.model.dao.IRespuestaDao;

@Service
public class RespuestaServiceImpl implements IRespuestaService{
    
    @Autowired
    private IRespuestaDao respuestaDao;

    @Override
    public List<Respuesta> findAll() {
        return respuestaDao.findAll();
    }

    @Override
    public Respuesta findById(Long idEntidad) {
        return respuestaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Respuesta save(Respuesta entidad) {
        return save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        respuestaDao.deleteById(idEntidad);
    }

    @Override
    public Respuesta guardarRespuesta(Respuesta respuesta) {
        return respuestaDao.save(respuesta);
    }

    @Override
    public List<Respuesta> obtenerRespuestaPorEstudiante(Estudiante estudiante) {
        return respuestaDao.findByEstudiante(estudiante);
    }
}
