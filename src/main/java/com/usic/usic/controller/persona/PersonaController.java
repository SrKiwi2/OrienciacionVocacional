package com.usic.usic.controller.persona;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class PersonaController {
    
    @GetMapping(value = "/vista-persona")
    public String vistaPersona(Model model) {
        
        return "Persona/registro-persona";
    }
    
    
}
