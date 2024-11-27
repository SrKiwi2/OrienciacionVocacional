package com.usic.usic.model.Service;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.DTO.PersonaDTO;
import com.usic.usic.model.Entity.Persona;

@Service
public interface IPersonaService extends IServiceGenerico <Persona, Long> {
    
    Persona validarCI(@Param("ci") String ci);

    Persona findByCorreo(String correo);

    PersonaDTO obtenerPersonaPorCi(String ci);

    Persona buscarPersonaPorCI(String ci);
}
