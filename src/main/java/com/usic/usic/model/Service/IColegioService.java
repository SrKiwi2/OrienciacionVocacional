package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Colegio;

@Service
public interface IColegioService extends IServiceGenerico <Colegio, Long>{
    
    Colegio buscarColegio(String nombreColegio);

}
