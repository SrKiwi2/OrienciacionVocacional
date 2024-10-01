package com.usic.usic.controller.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.usic.usic.anotaciones.ValidarUsuarioAutenticado;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;

import jakarta.servlet.http.HttpServletResponse;

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

    @GetMapping("/")
    public String getMethodName() {
        return "redirect:/vista-test";
    }
    
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

    @GetMapping(value = "/tests")
    public String tipo_test(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return "test/tipo_test";
    }
}
