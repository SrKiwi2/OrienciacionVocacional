package com.usic.usic.controller.Test;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class TestController {
    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired  
    private INacionalidadService nacionalidadService;

    @Autowired
    private ITipoTestService tipoTestService; 

    @Autowired
    private IPreguntaService preguntaService;

    @GetMapping("/")
    public String getMethodName( HttpServletResponse response) {
        return "redirect:/orientacion_vocacional";
    }
    
    @GetMapping(value = "/orientacion_vocacional")
    public String vistaPersona(@Validated Persona persona, Model model, HttpServletResponse response) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("persona", new Persona());
        return "test/inicio_test";
    }

    @GetMapping(value = "/tests")
    public String tipo_test(HttpServletResponse response, Model model) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        model.addAttribute("preTest", tipoTestService.findById(1L));
        model.addAttribute("HabiSocial", tipoTestService.findById(2L));
        model.addAttribute("InterMulti", tipoTestService.findById(3L));
        model.addAttribute("InteProfe", tipoTestService.findById(4L));
        return "test/vista_tests";
    }

    @GetMapping(value = "/tests1")
    public String tipo_test1(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return "test/prueba";
    }

    @PostMapping("/validarFechasTest/{idTipoTest}")
    public ResponseEntity<String> validarFechasTest(@PathVariable Long idTipoTest, HttpSession session) {
        
        try {
            TipoTest tipoTest = tipoTestService.findById(idTipoTest);
            LocalDate fechaActual = LocalDate.now();

            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());

            Long idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);

            if (tipoTest.getFechaInicio() == null || tipoTest.getFechaFin() == null) {
                return ResponseEntity.ok("Este test aún no está habilitado porque no tiene fechas asignadas.");
            }

            if (idPregunta == 0) {
                return ResponseEntity.ok("Ya has realizado este test: " + tipoTest.getTipoTest());
            } else {
                if ((fechaActual.isEqual(tipoTest.getFechaInicio()) || fechaActual.isAfter(tipoTest.getFechaInicio())) &&
                    (fechaActual.isEqual(tipoTest.getFechaFin()) || fechaActual.isBefore(tipoTest.getFechaFin()))) {
                    return ResponseEntity.ok("Bienvenido al test vocacional de habilidades sociales, este test tiene el fin de: " + tipoTest.getDescripcion());
                } else {
                    return ResponseEntity.ok("Este Test no está vigente, las fechas válidas son desde " + tipoTest.getFechaInicio() + " hasta " + tipoTest.getFechaFin());
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al acceder al test: " + e.getMessage());
        }
    }
    @GetMapping(value = "/reporte_test2")
    public String reporte_test2() {
        
        return "test/resultado_tests/reporte_prueba_resultados";
    }
}
