package com.usic.usic.model.IServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Service.ITipoTestService;
import com.usic.usic.model.dao.ITipoTestDao;

@Service
public class TipoTestServiceImpl implements ITipoTestService{

    @Autowired
    private ITipoTestDao tipoTestDao;

    @Override
    public List<TipoTest> findAll() {
        return tipoTestDao.findAll();
    }

    @Override
    public TipoTest findById(Long idEntidad) {
        return tipoTestDao.findById(idEntidad).orElse(null);
    }

    @Override
    public TipoTest save(TipoTest entidad) {
        return tipoTestDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        tipoTestDao.deleteById(idEntidad);
    }

    @Override
    public TipoTest findByTipoTest(String tipoTest) {
        return tipoTestDao.findByTipoTest(tipoTest);
    }

    @Override
    public List<TipoTest> findTestsHabilitados(LocalDate fechaActual) {
        return tipoTestDao.findTestsHabilitados(fechaActual);
    }

    @Override
    public Long countDistinctPreguntasNotRespondidas(Long id_tipo_test, Long idEstudiante) {
        return tipoTestDao.countDistinctPreguntasNotRespondidas(id_tipo_test, idEstudiante);
    }
}
