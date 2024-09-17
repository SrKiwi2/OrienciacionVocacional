package com.usic.usic.model.Entity;

import com.usic.usic.config.AuditoriaConfig;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Nacionalidad
 */
@Entity
@Table(name = "nacionalidad")
@Setter
@Getter
public class Nacionalidad extends AuditoriaConfig{

    private static final Long serialVersionUID = 2629195288020321924L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nacionalidad")
    private Long idNacionalidad;

    @Column(name = "nacionalidad")
    private String nombreNacionalidad;
}