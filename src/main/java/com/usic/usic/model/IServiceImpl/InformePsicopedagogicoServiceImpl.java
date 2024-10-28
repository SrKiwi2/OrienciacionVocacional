package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.InformePsicopedagoga;
import com.usic.usic.model.Service.InfomePsicopedagogicoService;
import com.usic.usic.model.dao.InformePsicopedagogicoDao;

@Service
public class InformePsicopedagogicoServiceImpl implements InfomePsicopedagogicoService{

    @Autowired
    private InformePsicopedagogicoDao informePsicopedagogicoDao;

    @Override
    public List<InformePsicopedagoga> findAll() {
        return informePsicopedagogicoDao.findAll();
    }

    @Override
    public InformePsicopedagoga findById(Long idEntidad) {
        return informePsicopedagogicoDao.findById(idEntidad).orElse(null);
    }

    @Override
    public InformePsicopedagoga save(InformePsicopedagoga entidad) {
        return informePsicopedagogicoDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        informePsicopedagogicoDao.deleteById(idEntidad);
    }
    
}
