package com.usic.usic.controller.estudiante;

import java.util.Optional;

import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;
@Controller
public class AdministrarEstudiantesController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private INacionalidadService nacionalidadService;
    
    @GetMapping(value = "/administracion_estudiante")
    public String administracionEstudianteVistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "Estudiante/admin-estudiantes/adm_estudiante";
    }

    @GetMapping("/formularioEditEstudiante/{idEstudiante}") // Formulario Edit Estudiante
    public String formulariEditEstudiante(@PathVariable("idEstudiante") Long idEstudiante, Model model) {
        model.addAttribute("estudiante", estudianteService.findById(idEstudiante));
        model.addAttribute("colegioss", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("edit", "true");
        return "Estudiante/admin-estudiantes/form_adm_estudiante";
    }

    @GetMapping("/datosEstudiante/{idEstudiante}")
    public String datosEstudiante(@PathVariable("idEstudiante") Long idEstudiante, Model model) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("colegioss", colegioService.findAll());
        model.addAttribute("edit", "false");
        return "Estudiante/admin-estudiantes/form_adm_estudiante";
    }

    @PostMapping("/editarEstudiante")
    public ResponseEntity<String> editarEstudiante(@ModelAttribute Estudiante estudiante, 
        @RequestParam(value = "nombre") String nombre,
        @RequestParam(value = "paterno") String paterno,
        @RequestParam(value = "materno") String materno,
        @RequestParam(value = "ci") String ci,
        @RequestParam(value = "correo") String correo,
        @RequestParam(value = "genero") Long idGenero,
        @RequestParam(value = "colegio") long idColegio,
        @RequestParam(value = "nacionalidad") Long idNacionalidad) {

        estudiante.setColegio(colegioService.findById(idColegio));

        Persona persona = personaService.validarCI(ci);
        if (persona != null) {
            persona.setNombre(nombre);
            persona.setPaterno(paterno);
            persona.setMaterno(materno);
            persona.setCi(ci);
            persona.setCorreo(correo);
            persona.setGenero(generoService.findById(idGenero));
            persona.setNacionalidad(nacionalidadService.findById(idNacionalidad));
            personaService.save(persona);
            estudiante.setPersona(persona);

            Colegio colegio = colegioService.findById(idColegio);
            estudiante.setColegio(colegio);

            estudiante.setEstado("HABILITADO");
            return ResponseEntity.ok("modificado");
        }else{
            return ResponseEntity.ok("error");
        }
    }

}
