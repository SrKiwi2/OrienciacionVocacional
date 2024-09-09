package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Rol;

@Service
public interface IRolService extends IServiceGenerico <Rol, Long>{
    
    Rol buscarPorNombre(String nombre);
}
