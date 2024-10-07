package com.usic.usic.model.Service;

import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Usuario;

@Service
public interface IUsuarioService extends IServiceGenerico<Usuario, Long> {
    
    Usuario getUsuarioPassword(String username, String password);

    Usuario buscarPorUsuario(String username);

    Usuario findByPersona(Persona persona);

    Usuario findByUsername (String username);

    String findEstadoByCorreoOrDefault(@Param("correo") String correo);

    Optional<Usuario> findByCorreo(@Param("correo") String correo);
}
