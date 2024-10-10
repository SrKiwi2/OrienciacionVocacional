package com.usic.usic.controller.Administración;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Service.IEstudianteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PsicopedagogaController {

    @Autowired
    private IEstudianteService estudianteService;
    
    @GetMapping("/vista_psicopedagoga")
    public String vistaPsicopedagoga() {
        return "Administracion/psicopedagoga/vista_psicopedagoga";
    }

        @PostMapping("/listarSeguimiento")
    public String ListarSeguimiento(HttpServletRequest request, Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "Administracion/psicopedagoga/tabla_seguimiento";
    }
    
}
