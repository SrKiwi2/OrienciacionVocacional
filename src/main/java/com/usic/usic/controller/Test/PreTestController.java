package com.usic.usic.controller.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Entity.EstudianteRespuesta;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IPreguntaService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PreTestController {
    
    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPreguntaService preguntaService;
    
    @GetMapping("/pre_test")
    public String pre_test(Model model) {

        model.addAttribute("registro_pre_test", new EstudianteRespuesta());

        // model.addAttribute("pregunta", preguntaService.findAll());

        // model.addAttribute("respuestas", preguntaService.findAll());

        //Continuar................



        return "test/vista_pregunta";
    }
    
}
