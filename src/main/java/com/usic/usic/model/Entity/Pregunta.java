package com.usic.usic.model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.usic.usic.config.AuditoriaConfig;

@Entity
@Setter
@Getter
@Table(name = "pregunta")
public class Pregunta extends AuditoriaConfig{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPregunta;

    private String pregunta;

    @ManyToOne
    @JoinColumn(name = "id_tipo_test")
    private TipoTest tipoTest;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pregunta")
    private TipoPregunta tipoPregunta;
}
