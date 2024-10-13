package com.usic.usic.model.Entity;

import java.time.LocalDate;

import com.usic.usic.config.AuditoriaConfig;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tipoTest")
public class TipoTest extends AuditoriaConfig{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_test;

    @Column(name = "tipo_test")
    private String tipoTest;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String descripcion;
}

