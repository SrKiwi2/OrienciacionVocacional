package com.usic.usic.model.IService;

import org.springframework.stereotype.Service;

import com.usic.usic.model.entity.Usuario;

@Service
public interface IUsuarioService extends IServiceGenerico<Usuario, Long> {
    
    Usuario getUsuarioPassword(String username, String password);

    Usuario buscarPorUsuario(String username);
}
