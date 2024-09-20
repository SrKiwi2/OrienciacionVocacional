package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;

import jakarta.servlet.http.HttpServletRequest;


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

    @GetMapping("/formularioEditEstudinateVista/{idEstudiante}")
    public String formularioEditEstudianteVista(@PathVariable("idEstudiante") Long idEstudiante, Model model) {
        model.addAttribute("estudiante", estudianteService.findById(idEstudiante));
        model.addAttribute("colegioss", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        return "Estudiante/admin-estudiantes/form_edit_estudiante";
    }

    @GetMapping("/datosEstudiante/{idEstudiante}")
    public String datosEstudiante(@PathVariable("idEstudiante") Long idEstudiante, Model model) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("colegioss", colegioService.findAll());
        return "Estudiante/admin-estudiantes/form_edit_estudiante";
    }

    @PostMapping("/editarEstudianteVista")
    public ResponseEntity<String> editarEstudianteVista(@ModelAttribute Estudiante estudiante) {
        estudiante.setEstado("HABILITADO");
        estudianteService.save(estudiante);
        return ResponseEntity.ok("Se modificó el registro con éxito");
    }
}
