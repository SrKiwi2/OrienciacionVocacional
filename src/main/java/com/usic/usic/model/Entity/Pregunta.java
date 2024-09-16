package com.usic.usic.model.Entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.usic.usic.config.AuditoriaConfig;

@Entity
@Setter
@Getter
public class Pregunta extends AuditoriaConfig{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPregunta;

    private String texto;

    @ElementCollection(targetClass = String.class)
    private List<String> opciones;
}
