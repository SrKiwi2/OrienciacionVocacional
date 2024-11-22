package com.usic.usic.model.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Facultad;

@Service
public interface IFacultadService extends IServiceGenerico<Facultad, Long>{
    List<Facultad> listarFacultades();
    Facultad buscarFacultadPorNombre(String facultad);
}
