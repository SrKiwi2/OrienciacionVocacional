package com.usic.usic.controller.Colegio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.usic.usic.model.IService.IColegioService;

@Controller
public class ColegioController {
    
    @Autowired
    private IColegioService colegioService;

    @GetMapping(value = "/administrar-colegio")
    public String administrarColegio(Model model) {
        
        return "Complementos/Colegio/vista-colegio";
    }
}
