package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Genero;

@Service
public interface IGeneroService extends IServiceGenerico<Genero, Long>{
    Genero buscarPorGenero(String genero);
}
