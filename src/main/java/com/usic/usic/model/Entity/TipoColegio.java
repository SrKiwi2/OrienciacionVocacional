package com.usic.usic.model.Entity;

import java.util.List;
import com.usic.usic.config.AuditoriaConfig;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tipo_colegio")
public class TipoColegio extends AuditoriaConfig{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_colegio")
    private Long idTipoColegio;

    @Column(name = "nombre_tipo_colegio")
    private String nombreTipoColegio;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo_colegio", fetch = FetchType.LAZY)
    private List<Colegio> colegios;

}
