package com.usic.usic.controller.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String intereses_profesionales(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());
        System.out.println(estudiante);
        Long idTipoTest = 3L;
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

    @PostMapping("/guardar_respuesta_IP")
    public String guardar_respuesta_ip(@RequestParam("respuesta_pregunta") Long respuesta_pregunta, HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Respuesta respuesta = respuestaService.findById(respuesta_pregunta);
        EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
        estudianteRespuesta.setEstado("ACTIVO");
        estudianteRespuesta.setComplemento("n/a");
        estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
        estudianteRespuesta.setRespuesta(respuesta);
        estudianteRespuestaService.save(estudianteRespuesta);
        return "redirect:/pre_test";
    }
}
