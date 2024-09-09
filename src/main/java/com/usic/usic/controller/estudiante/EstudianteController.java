package com.usic.usic.controller.estudiante;

import org.springframework.stereotype.Controller;

import com.usic.usic.model.Entity.Colegio;

import ch.qos.logback.core.model.Model;

@Controller
public class EstudianteController {

    public String getEstudiante(Model model) {

        return "estudiante";
    }
    

    // Colegio colegio = colegioService.findById(idColegio);
    //     if (colegio == null) {
    //         colegio = new Colegio();
    //     }
}
