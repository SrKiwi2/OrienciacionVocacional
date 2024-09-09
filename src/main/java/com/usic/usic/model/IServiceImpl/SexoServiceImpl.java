package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Service.ISexoService;
import com.usic.usic.model.dao.ISexoDao;
import com.usic.usic.model.Entity.Genero;

@Service
public class SexoServiceImpl implements ISexoService{

    @Autowired
    private ISexoDao sexoDao;

    @Override
    public List<Genero> findAll() {
        return sexoDao.findAll();
    }

    @Override
    public Genero findById(Long idEntidad) {
        return sexoDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Genero save(Genero entidad) {
        return sexoDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        sexoDao.deleteById(idEntidad);
    }

    @Override
    public Genero buscarPorSexo(String sexo) {
        return sexoDao.findByNombreSexo(sexo);
    }
    
}
