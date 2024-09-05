package com.usic.usic.model.IService;

import org.springframework.stereotype.Service;

import com.usic.usic.model.entity.Colegio;

@Service
public interface IColegioService extends IServiceGenerico <Colegio, Long>{
    
    Colegio buscarColegio(String nombre);

}
