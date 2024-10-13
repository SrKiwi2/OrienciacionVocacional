package com.usic.usic.model.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.TipoTest;

@Service
public interface ITipoTestService extends IServiceGenerico<TipoTest, Long>{
    TipoTest findByTipoTest(String tipoTest);

    List<TipoTest> findTestsHabilitados(@Param("fechaActual") LocalDate fechaActual);
}
