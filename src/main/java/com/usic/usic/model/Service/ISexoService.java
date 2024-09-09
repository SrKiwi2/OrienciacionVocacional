package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Genero;

@Service
public interface ISexoService extends IServiceGenerico<Genero, Long>{
    Genero buscarPorSexo(String nombreSexo);
}
