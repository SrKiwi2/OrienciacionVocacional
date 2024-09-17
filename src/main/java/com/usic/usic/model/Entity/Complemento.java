package com.usic.usic.model.Entity;

import com.usic.usic.config.AuditoriaConfig;

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
@Table(name = "complemento")
public class Complemento extends AuditoriaConfig{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_complemento;

    private String complemento;
}
