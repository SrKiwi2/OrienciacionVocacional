package com.usic.usic.controller.Test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.EstudianteRespuesta;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IPreguntaService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PreTestController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPreguntaService preguntaService;

    @Autowired
    private IEstudianteService estudianteService;
    
    @GetMapping("/pre_test")
    public String pre_test(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());

        Long idTipoTest = 1L;

        Optional<Long> idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);

        if (idPregunta.isPresent()) {

            Pregunta pregunta = preguntaService.findById(idPregunta.get());
            
            model.addAttribute("pregunta", pregunta);

            System.out.println("Primera pregunta para el estudiante: " + pregunta.getPregunta());
        } else {

            model.addAttribute("pregunta", "No hay preguntas disponibles.");
        }

        model.addAttribute("registro_pre_test", new EstudianteRespuesta());
        return "test/vista_pregunta";
    } 
}



        // model.addAttribute("pregunta", preguntaService.findAll()); 

        // model.addAttribute("respuestas", preguntaService.findAll());

        //Continuar................



        // OBTENER LA SPREGUNTAS SEGUN EL TIPO TEST
