package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Carrera;

@Service
public interface ICarreraService extends IServiceGenerico<Carrera, Long>{
    Carrera findByCarrera(String carrera);
}
