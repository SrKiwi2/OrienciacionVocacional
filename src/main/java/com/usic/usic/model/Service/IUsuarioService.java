package com.usic.usic.model.Service;

import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Usuario;

@Service
public interface IUsuarioService extends IServiceGenerico<Usuario, Long> {
    
    Usuario getUsuarioPassword(String username, String password);

    Usuario buscarPorUsuario(String username);

    Usuario findByPersona(Persona persona);

    Usuario findByUsername (String username);
}
