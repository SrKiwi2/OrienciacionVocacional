package com.usic.usic.controller.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestHabilidadesSocialesController {

    @GetMapping("/vista_habilidades_socialedds")
    public String vistaTestKardl() {
        return "/test/test_habilidades_sociales/habilidades_sociales.html";
    }
}
