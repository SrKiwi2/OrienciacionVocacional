package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
    @Query(value = "select * from usuario u where u.username = ?1 and u.password = ?2", nativeQuery = true)
    Usuario getUsuarioPassword(String username, String password);

    Usuario findByUsername (String username);

    Usuario findByPersona(Persona persona);
}
