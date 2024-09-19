package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
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
    public String FormularioEditEstudianteVista(HttpServletRequest request, Model model,
            @PathVariable("idEstudiante") Long idEstudiante) {

        String mode = request.getParameter("mode");
        boolean isEdit = "edit".equals(mode);

        model.addAttribute("estudiantess", estudianteService.findById(idEstudiante));
        model.addAttribute("colegioss", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("edit", isEdit);
        return "Estudiante/admin-estudiantes/form_adm_estudiante";
    }

    @PostMapping("/editarEstudianteVista")
    public ResponseEntity<String> editarEstudianteVista(@Validated Estudiante estudiante) {

        estudiante.setEstado("HABILITADO");
        estudianteService.save(estudiante);

        return ResponseEntity.ok("Se modificó el registro con éxito");
    }
}
