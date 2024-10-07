package com.usic.usic.controller.pregunta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.Service.ITipoPreguntaService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PreguntaController {

    @Autowired
    private IPreguntaService preguntaService;

    @Autowired
    private ITipoPreguntaService tipoPreguntaService;

    @Autowired
    private ITipoTestService tipoTestService;

    @GetMapping(value = "/adm_vista_pregunta")
    public String administracionEstudianteVistaPersona(Model model) {
        
        return "pregunta/vista_pregunta";
    }

    @PostMapping("/listarPreguntas")
    public String listarPreguntas(HttpServletRequest request, Model model) {
        model.addAttribute("preguntas", preguntaService.findAll());
        model.addAttribute("tipoPregunta", tipoPreguntaService.findAll());
        model.addAttribute("tipoTest", tipoTestService.findAll());
        return "pregunta/tabla_pregunta";
    }

    @PostMapping("/FormularioPregunta")
    public String formularioPregunta(HttpServletRequest request, Model model) {
        model.addAttribute("pregunta", preguntaService.findAll());
        model.addAttribute("tipoPregunta", tipoPreguntaService.findAll());
        model.addAttribute("tipoTest", tipoTestService.findAll());
        model.addAttribute("preguntass", new Pregunta());

        return "pregunta/formulario_pregunta";
       
    }

    @GetMapping("/formularioEditPregunta/{idPregunta}")
    public String formularioEditPregunta(@PathVariable("idPregunta") Long idPregunta, Model model) {
        model.addAttribute("preguntas", preguntaService.findById(idPregunta));
        model.addAttribute("tipoPregunta", tipoPreguntaService.findAll());
        model.addAttribute("tipoTest", tipoTestService.findAll());
        model.addAttribute("edit", "true");
        return "pregunta/formulario_pregunta";
    }

}
