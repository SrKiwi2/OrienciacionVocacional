package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.TipoTest;

public interface ITipoTestDao extends JpaRepository<TipoTest, Long> {
    TipoTest findByTipoTest(String tipoTest);
}
