package com.usic.usic.model.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Carrera;

@Service
public interface ICarreraService extends IServiceGenerico<Carrera, Long>{
    Carrera findByCarrera(String carrera);
    void insertInformeCarrera(Long idInformePsicopedagoga, Long idCarrera);
    Carrera buscarCarreraPorNombre(String carrera);
    List<Carrera> listarCarreras();
}
