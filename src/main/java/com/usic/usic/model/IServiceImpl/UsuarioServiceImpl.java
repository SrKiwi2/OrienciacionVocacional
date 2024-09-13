package com.usic.usic.model.IServiceImpl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IUsuarioService;
import com.usic.usic.model.dao.IUsuarioDao;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }

    @Override
    public Usuario findById(Long idEntidad) {
        return usuarioDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Usuario save(Usuario entidad) {
        return usuarioDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        usuarioDao.deleteById(idEntidad);
    }

    @Override
    public Usuario getUsuarioPassword(String username, String password) {
        return usuarioDao.getUsuarioPassword(username, password);
    }

    @Override
    public Usuario buscarPorUsuario(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    public Usuario findByPersona(Persona persona) {
        return usuarioDao.findByPersona(persona);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
    
}
