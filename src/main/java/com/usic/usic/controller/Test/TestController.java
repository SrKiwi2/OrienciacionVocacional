package com.usic.usic.controller.Test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.usic.usic.model.Entity.Persona;

@Controller
public class TestController {
    
    @GetMapping(value = "/vista-test")
    public String vistaPersona(@Validated Persona persona, Model model) {
        
        model.addAttribute("persona", new Persona());

        return "test/vista_registro1";
    }
}
