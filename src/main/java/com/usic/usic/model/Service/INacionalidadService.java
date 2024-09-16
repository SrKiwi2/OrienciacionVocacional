package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Nacionalidad;

@Service
public interface INacionalidadService extends IServiceGenerico<Nacionalidad, Long> {
    Nacionalidad buscarNacionalidad(String nacionalidad);
}
