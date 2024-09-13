package com.usic.usic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usic.usic.model.Entity.Test;

public interface ITestDao extends JpaRepository<Test, Long> {
    Test findByNombreTest(String nombreTest);
}
