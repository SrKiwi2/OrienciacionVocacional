package com.usic.usic.model.Entity;

import com.usic.usic.config.AuditoriaConfig;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Respuesta extends AuditoriaConfig{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRespuesta;

    private String respuesta;

    @ManyToOne
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @ManyToOne
    @JoinColumn(name = "id_complemento")
    private Complemento complemento;
}
