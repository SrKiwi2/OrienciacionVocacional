package com.usic.usic.model.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PersonaDTO {
    private String nombres;
    private String paterno;
    private String materno;
    private String ci;
    private String genero;
    private String nacionalidad;
    private String correo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fec_nacimiento;
    
    private String colegio;
    private String url_certificado;
    private String testRealizado;

    public PersonaDTO(String nombres, String paterno, String materno, String ci, 
                      String genero, String nacionalidad, String correo, 
                      Date fec_nacimiento, String colegio,String url_certificado, String testRealizado) {
        this.nombres = nombres;
        this.paterno = paterno;
        this.materno = materno;
        this.ci = ci;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.correo = correo;
        this.fec_nacimiento = fec_nacimiento;
        this.colegio = colegio;
        this.url_certificado = url_certificado;
        this.testRealizado = testRealizado;
    }
}
