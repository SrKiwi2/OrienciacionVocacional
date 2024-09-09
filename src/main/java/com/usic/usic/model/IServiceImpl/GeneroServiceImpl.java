package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.dao.IGeneroDao;
import com.usic.usic.model.Entity.Genero;

@Service
public class GeneroServiceImpl implements IGeneroService{

    @Autowired
    private IGeneroDao generoDao;

    @Override
    public List<Genero> findAll() {
        return generoDao.findAll();
    }

    @Override
    public Genero findById(Long idEntidad) {
        return generoDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Genero save(Genero entidad) {
        return generoDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        generoDao.deleteById(idEntidad);
    }

    @Override
    public Genero buscarPorGenero(String sexo) {
        return generoDao.findByGenero(sexo);
    }
    
}
