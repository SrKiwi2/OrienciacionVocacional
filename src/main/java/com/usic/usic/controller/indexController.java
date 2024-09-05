package com.usic.usic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.usic.usic.anotaciones.ValidarUsuarioAutenticado;

@Controller
public class indexController {
    
    @ValidarUsuarioAutenticado
    @GetMapping(value = "/vista-administrador")
    public String VistaAdministrador() {

        return "vista-admin.html";
    }
    
}
