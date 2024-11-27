package com.usic.usic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usic.usic.anotaciones.ValidarUsuarioAutenticado;

@Controller
@RequestMapping("/admin")
public class indexController {
    
    @ValidarUsuarioAutenticado
    @GetMapping(value = "/vista-administrador")
    public String VistaAdministrador() {

        return "/vista_admin/vista-admin";
    }

    @ValidarUsuarioAutenticado
    @GetMapping("/contenido-admin")
    public String contenidoAdmin(Model model) throws Exception {
        return "vista_admin/contenido";
    }
    
}
