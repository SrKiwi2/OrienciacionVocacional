package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
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
    
    @GetMapping(value = "/administracion_estudiante")
    public String administracionEstudianteVistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "Estudiante/admin-estudiantes/adm_estudiante";
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public String verDatosEstudiante(@PathVariable Long idEstudiante, Model model) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);  // Asumiendo que tienes un servicio que encuentra al estudiante por ID
        model.addAttribute("estudiante", estudiante);
        return "vistaConModal";  // Nombre de tu plantilla HTML
    }
}
