package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Test;
import com.usic.usic.model.Service.ITestService;
import com.usic.usic.model.dao.IPreguntaDao;
import com.usic.usic.model.dao.ITestDao;

@Service
public class TestServiceImpl implements ITestService{

    @Autowired
    private ITestDao testDao;

    @Autowired
    private IPreguntaDao preguntaDao;

    @Override
    public List<Test> findAll() {
        return testDao.findAll();
    }

    @Override
    public Test findById(Long idEntidad) {
        return testDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Test save(Test entidad) {
        return testDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        testDao.deleteById(idEntidad);
    }

    @Override
    public Test obtenerTestPorNombre(String nombreTest) {
        return testDao.findByNombreTest(nombreTest);
    }

    @Override
    public List<Pregunta> obtenerPreguntaDelTest(Test test) {
        return preguntaDao.findByTest(test);
    }
    
}
