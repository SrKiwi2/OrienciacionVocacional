package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IUsuarioService;

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

    @Autowired
    private IUsuarioService usuarioService;
    
    @GetMapping(value = "/administracion_estudiante")
    public String administracionEstudianteVistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("usuario", usuarioService.findAll());
        return "Estudiante/admin-estudiantes/adm_estudiante";
    }

    @GetMapping("/formularioEditEstudiante/{idEstudiante}")
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
    public ResponseEntity<String> editarEstudiante(
            @ModelAttribute Estudiante estudiante,
            @RequestParam(value = "genero") Long idGenero,
            @RequestParam(value = "nacionalidad") Long idNacionalidad,
            @RequestParam(value = "colegio") Long idColegio) {

        if (estudiante == null || estudiante.getIdEstudiante() == null) {
            return ResponseEntity.badRequest().body("Estudiante no encontrado.");
        }

        Estudiante estudianteExistente = estudianteService.findById(estudiante.getIdEstudiante());
        if (estudianteExistente == null) {
            return ResponseEntity.badRequest().body("Estudiante no encontrado.");
        }

        Persona persona = estudianteExistente.getPersona();
        if (persona == null) {
            return ResponseEntity.badRequest().body("Persona asociada no encontrada.");
        }

        persona.setCi(estudiante.getPersona().getCi());
        persona.setNombre(estudiante.getPersona().getNombre());
        persona.setPaterno(estudiante.getPersona().getPaterno());
        persona.setMaterno(estudiante.getPersona().getMaterno());
        persona.setCorreo(estudiante.getPersona().getCorreo());
        persona.setGenero(generoService.findById(idGenero));
        persona.setFecha(estudiante.getPersona().getFecha());
        persona.setNacionalidad(nacionalidadService.findById(idNacionalidad));
        persona.setEstado("E");
        
        estudiante.setPersona(persona);
        estudiante.setColegio(colegioService.findById(idColegio));
        estudiante.setEstado("HABILITADO");

        personaService.save(persona);
        estudianteService.save(estudiante);

        return ResponseEntity.ok("modificado");
    }

    @PostMapping("/listarAdmEstudiante")
    public String listarAdmEstudiante(HttpServletRequest request, Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "Estudiante/admin-estudiantes/tabla_adm_estudiante";
    }
}
