package com.usic.usic.controller.Test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    
    @GetMapping(value = "/vista-test")
    public String vistaPersona(Model model) {
        
        return "test/vista_registro";
    }
}
