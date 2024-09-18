package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Respuesta;
import com.usic.usic.model.Service.IRespuestaService;
import com.usic.usic.model.dao.IRespuestaDao;

@Service
public class RespuestaServiceImpl implements IRespuestaService{

    @Autowired
    private IRespuestaDao respuestaDao;

    @Override
    public List<Respuesta> findAll() {
        // TODO Auto-generated method stub
        return respuestaDao.findAll();
    }

    @Override
    public Respuesta findById(Long idEntidad) {
        // TODO Auto-generated method stub
        return respuestaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Respuesta save(Respuesta entidad) {
        // TODO Auto-generated method stub
        return respuestaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        // TODO Auto-generated method stub
        respuestaDao.deleteById(idEntidad);
    }
    
}
