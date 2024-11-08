package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.usic.usic.model.Entity.InformePsicopedagoga;

public interface InformePsicopedagogicoDao extends JpaRepository<InformePsicopedagoga, Long> {
}
