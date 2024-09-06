package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.IService.ISexoService;
import com.usic.usic.model.dao.ISexoDao;
import com.usic.usic.model.entity.Sexo;

@Service
public class SexoServiceImpl implements ISexoService{

    @Autowired
    private ISexoDao sexoDao;

    @Override
    public List<Sexo> findAll() {
        return sexoDao.findAll();
    }

    @Override
    public Sexo findById(Long idEntidad) {
        return sexoDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Sexo save(Sexo entidad) {
        return sexoDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        sexoDao.deleteById(idEntidad);
    }

    @Override
    public Sexo buscarPorSexo(String sexo) {
        return sexoDao.findByNombreSexo(sexo);
    }
    
}
