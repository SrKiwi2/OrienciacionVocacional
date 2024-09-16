package com.usic.usic.controller.Test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Entity.EstudianteRespuesta;


@Controller
public class PreTestController {
    
    
    @GetMapping("/pre_test")
    public String pre_test(Model model, @PathVariable("idPregunta")Long idPregunta) {

        model.addAttribute("registro_pre_test", new EstudianteRespuesta());

        return "test/vista_pregunta";
    }
    
}
