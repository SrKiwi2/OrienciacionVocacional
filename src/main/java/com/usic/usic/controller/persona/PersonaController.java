package com.usic.usic.controller.persona;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.ISexoService;
import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Genero;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private ISexoService sexoService;
    
    @GetMapping(value = "/vista-persona")
    public String vistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll()); // Lista de colegios
        model.addAttribute("sexos", sexoService.findAll());
        model.addAttribute("persona", new Persona());
        return "Persona/registro-persona";
    }

    @GetMapping("/persona/{idPersona}")
    @ResponseBody
    public Persona obtenerPersona(@PathVariable("idPersona") Long idPersona) {
        return personaService.findById(idPersona);
    }

    @PostMapping(value = "/guardar-persona")
    public String guardarPersona(
        @RequestParam(value = "idPersona", required = false) Long idPersona,
        @RequestParam("nombre") String nombre,
        @RequestParam("paterno") String paterno,
        @RequestParam("materno") String materno,
        @RequestParam("ci") String ci,
        @RequestParam("correo") String correo,
        @RequestParam("idGenero") Long idGenero,
        Model model) {

        Persona persona = personaService.validarCI(ci);
        if (persona == null) {
            persona = new Persona();
        }
        persona.setCi(ci);
        persona.setNombre(nombre);
        persona.setPaterno(paterno);
        persona.setMaterno(materno);
        persona.setCorreo(correo);
        persona.setEstado("ACTIVO");
        
        Genero genero = sexoService.findById(idGenero);
        if (genero == null) {
            genero = new Genero();
            persona.setGenero(genero);
        }
        persona.setGenero(genero);  

        personaService.save(persona);
        return "redirect:/vista-persona";
    }

    @PostMapping(value = "/eliminar-persona/{idPersona}")
    public ResponseEntity<String> eliminarPersona(@PathVariable("idPersona") Long idPersona) {
        personaService.deleteById(idPersona);
        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }
}
