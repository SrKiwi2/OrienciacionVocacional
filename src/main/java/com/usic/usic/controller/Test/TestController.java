package com.usic.usic.controller.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;

@Controller
public class TestController {
    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired  
    private INacionalidadService nacionalidadService;
    
    @GetMapping(value = "/vista-test")
    public String vistaPersona(@Validated Persona persona, Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("persona", new Persona());

        return "test/vista_registro1";
    }
}
