package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Service.IEstudianteService;

@Controller
public class EstudianteController {

    @Autowired
    private IEstudianteService estudianteService;

    @GetMapping(value = "regEstudiante")
    public String getEstudiante(Model model) {

        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("estudiante", new Estudiante());

        return "test/";
    }
    

    // Colegio colegio = colegioService.findById(idColegio);
    //     if (colegio == null) {
    //         colegio = new Colegio();
    //     }
}
