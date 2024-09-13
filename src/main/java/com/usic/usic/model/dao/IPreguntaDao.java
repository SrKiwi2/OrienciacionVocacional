package com.usic.usic.model.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Test;

public interface IPreguntaDao extends JpaRepository<Pregunta, Long>{
   List<Pregunta> findByTest(Test test);
}
