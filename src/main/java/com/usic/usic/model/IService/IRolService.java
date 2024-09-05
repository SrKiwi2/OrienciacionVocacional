package com.usic.usic.model.IService;

import org.springframework.stereotype.Service;

import com.usic.usic.model.entity.Rol;

@Service
public interface IRolService extends IServiceGenerico <Rol, Long>{
    
    Rol buscarPorNombre(String nombre);
}
