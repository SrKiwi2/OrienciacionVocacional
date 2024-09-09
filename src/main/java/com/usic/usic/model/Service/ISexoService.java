package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Sexo;

@Service
public interface ISexoService extends IServiceGenerico<Sexo, Long>{
    Sexo buscarPorSexo(String nombreSexo);
}
