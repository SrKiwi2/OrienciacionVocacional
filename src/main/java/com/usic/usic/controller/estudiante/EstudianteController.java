package com.usic.usic.controller.estudiante;

import org.springframework.stereotype.Controller;

import ch.qos.logback.core.model.Model;

@Controller
public class EstudianteController {

    public String getEstudiante(Model model) {

        return "estudiante";
    }
    
}
