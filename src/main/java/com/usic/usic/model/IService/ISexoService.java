package com.usic.usic.model.IService;

import org.springframework.stereotype.Service;

import com.usic.usic.model.entity.Sexo;

@Service
public interface ISexoService extends IServiceGenerico<Sexo, Long>{
    Sexo buscarPorSexo(String nombreSexo);
}
