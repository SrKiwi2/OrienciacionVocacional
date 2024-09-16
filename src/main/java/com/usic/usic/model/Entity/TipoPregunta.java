package com.usic.usic.model.Entity;

import com.usic.usic.config.AuditoriaConfig;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * TipoPregunta
 */
@Entity
@Table(name = "tipo_pregunta")
@Setter
@Getter
public class TipoPregunta extends AuditoriaConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_pregunta;

    private String tipo_pregunta;

    private String descripcion;
}