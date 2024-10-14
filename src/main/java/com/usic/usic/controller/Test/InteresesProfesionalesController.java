package com.usic.usic.controller.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.EstudianteRespuesta;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Respuesta;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Repository.Sp_preguntas;
import com.usic.usic.model.Service.IEstudianteRespuestaService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.Service.IRespuestaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class InteresesProfesionalesController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IPreguntaService preguntaService;

   @Autowired
    private IRespuestaService respuestaService;

    @Autowired
    private Sp_preguntas sp_preguntas;

    @Autowired
    private IEstudianteRespuestaService estudianteRespuestaService;

    @GetMapping("/intereses_profesionales")
    public String intereses_profesionales(@PathVariable Long idTipoTest, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());
        Long idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);
        model.addAttribute("respuestasRespondidas", sp_preguntas.ObtenerRespuestasrespondidas(estudiante.getIdEstudiante(), idTipoTest));

        if (idPregunta != 0) {

            Pregunta pregunta = preguntaService.findById(idPregunta);
            
            model.addAttribute("pregunta", pregunta);
            model.addAttribute("respuestas",  respuestaService.findAll());
            model.addAttribute("registro_pre_test", new EstudianteRespuesta());
            return "test/vista_pregunta";
        } else {

            model.addAttribute("pregunta", "No hay preguntas disponibles.");
            return "redirect:/interpretar_respuestas";
        }
    }
}
