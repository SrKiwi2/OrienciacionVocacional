package com.usic.usic.controller.Test;

import java.util.Date;
import java.util.List;
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
import com.usic.usic.model.Entity.Respuesta;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IEstudianteRespuestaService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.Service.IRespuestaService;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PreTestController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPreguntaService preguntaService;

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IRespuestaService respuestaService;

    @Autowired
    private IEstudianteRespuestaService estudianteRespuestaService;

    @Autowired
    private IUsuarioService usuarioService;

    
    @GetMapping("/pre_test")
    public String pre_test(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());

        Long idTipoTest = 1L;

        Optional<Long> idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);

        if (idPregunta.isPresent()) {

            Pregunta pregunta = preguntaService.findById(idPregunta.get());
            
            model.addAttribute("pregunta", pregunta);
            model.addAttribute("respuestas",  respuestaService.findAll());
            
        } else {

            model.addAttribute("pregunta", "No hay preguntas disponibles.");
        }

        model.addAttribute("registro_pre_test", new EstudianteRespuesta());
        return "test/vista_pregunta";
    } 


    @PostMapping("/guardar_respuesta")
    public String guardar_respuesta(@RequestParam("respuesta_pregunta") Long respuesta_pregunta, HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Respuesta respuesta = respuestaService.findById(respuesta_pregunta);

        EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();

        estudianteRespuesta.setEstado("ACTIVO");
        estudianteRespuesta.setComplemento("n/a");
        estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
        estudianteRespuesta.setRespuesta(respuesta);
        estudianteRespuestaService.save(estudianteRespuesta);

        System.out.println(respuesta_pregunta);
        
        return "redirect:/pre_test";
    }

    @PostMapping("/guardar_respuesta2")
    public String guardar_respuesta2(@RequestParam(value = "checkboxes", required = false) List<String> checkboxes,
                                    @RequestParam(value = "textInputs", required = false) List<String> textInputs, HttpServletRequest request) {
        Persona persona = (Persona) request.getSession().getAttribute("persona");
        
        if (checkboxes != null && textInputs != null) {
            // Procesar las respuestas seleccionadas
            for (int i = 0; i < checkboxes.size(); i++) {
                Long checkboxValue = Long.valueOf(checkboxes.get(i));
                String textValue = textInputs.get(i);

                Respuesta respuesta = respuestaService.findById(checkboxValue);
                // Aquí puedes hacer lo que desees con los valores obtenidos
                System.out.println("Checkbox: " + checkboxValue + ", Texto: " + textValue);

                EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
                estudianteRespuesta.setEstado("ACTIVO");
                estudianteRespuesta.setComplemento(textValue);
                estudianteRespuesta.setRespuesta(respuesta);
                estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
                estudianteRespuestaService.save(estudianteRespuesta);
            }
        }

        return "redirect:/pre_test";
    }
    
    @PostMapping("/guardar_respuesta3")
    public String guardar_respuesta3(@RequestParam("respuesta_pregunta") String respuesta_pregunta, @RequestParam("id_pregunta") Long id_pregunta, HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Usuario usuario = usuarioService.findByPersona(persona);
        Pregunta pregunta = preguntaService.findById(id_pregunta);

        Respuesta respuesta = new Respuesta();

        respuesta.setEstado("ACTIVO");
        respuesta.setModificacion(new Date());
        respuesta.setModificacionIdUsuario(usuario.getIdUsuario());
        respuesta.setRegistro(new Date());
        respuesta.setRegistroIdUsuario(usuario.getIdUsuario());
        respuesta.setRespuesta(respuesta_pregunta);
        respuesta.setPregunta(pregunta);
        respuestaService.save(respuesta);

        EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
        estudianteRespuesta.setEstado("ACTIVO");
        estudianteRespuesta.setComplemento("n/a");
        estudianteRespuesta.setRespuesta(respuesta);
        estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
        estudianteRespuesta.setModificacion(new Date());
        estudianteRespuesta.setModificacionIdUsuario(usuario.getIdUsuario());
        estudianteRespuesta.setRegistro(new Date());
        estudianteRespuesta.setRegistroIdUsuario(usuario.getIdUsuario());
        estudianteRespuestaService.save(estudianteRespuesta);


        return "redirect:/pre_test";
    }

    @PostMapping("/guardar_respuesta4")
    public String guardar_respuesta4(@RequestParam(value = "checkboxes2", required = false) List<String> checkboxes, @RequestParam(value = "textInputs", required = false) List<String> textInputs,HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Usuario usuario = usuarioService.findByPersona(persona);

        if (checkboxes != null) {
            for (int i = 0; i < checkboxes.size(); i++) {
                Long checkboxValue = Long.valueOf(checkboxes.get(i));
                Respuesta respuesta = respuestaService.findById(checkboxValue);
                EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
                estudianteRespuesta.setEstado("ACTIVO");
                estudianteRespuesta.setComplemento("n/a");
                estudianteRespuesta.setRespuesta(respuesta);
                estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
                estudianteRespuesta.setModificacion(new Date());
                estudianteRespuesta.setModificacionIdUsuario(usuario.getIdUsuario());
                estudianteRespuesta.setRegistro(new Date());
                estudianteRespuesta.setRegistroIdUsuario(usuario.getIdUsuario());
                estudianteRespuestaService.save(estudianteRespuesta);
            }
        }


        return "redirect:/pre_test";
    }



    @GetMapping("/pre_test_prueba")
    public String pre_test_prueba(Model model, HttpSession session) {
        
        return "test/pruebas/vista_test_prueba";
    } 

    @GetMapping("/vista_pregunta")
    public String vista_pregunta(Model model, HttpSession session) {

        
        return "test/pruebas/vista_pregunta";
    } 

}



        // model.addAttribute("pregunta", preguntaService.findAll()); 

        // model.addAttribute("respuestas", preguntaService.findAll());

        //Continuar................



        // OBTENER LA SPREGUNTAS SEGUN EL TIPO TEST
