package com.usic.usic.model.Service;

import java.util.List;

import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Test;

public interface ITestService extends IServiceGenerico<Test, Long>{
    Test obtenerTestPorNombre(String nombreTest);
    List<Pregunta> obtenerPreguntaDelTest(Test test);
}
