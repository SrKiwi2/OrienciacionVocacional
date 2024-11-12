package com.usic.usic.controller.persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IPersonaService;

import jakarta.servlet.http.HttpServletRequest;

import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.DTO.PersonaDTO;
import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Genero;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IGeneroService sexoService;
    
    @GetMapping(value = "/vista-persona")
    public String vistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll()); // Lista de colegios
        model.addAttribute("sexos", sexoService.findAll());
        model.addAttribute("persona", new Persona());
        return "Persona/registro-persona";
    }

    @PostMapping("/formularioPersona")
    public String formularioPersona(HttpServletRequest request, Model model) {
        model.addAttribute("persona", new Persona());
        model.addAttribute("genero", sexoService.findAll());
        return "Persona/formulario_persona";
    }

    @GetMapping("/formularioPersona/{idPersona}")
    public String editarPersonaFormulario(@PathVariable("idPersona") Long idPersona, Model model) {
        Persona persona = personaService.findById(idPersona);
        model.addAttribute("persona", persona);
        model.addAttribute("genero", sexoService.findAll());
        model.addAttribute("edit", true);
        return "Persona/formulario_persona";
    }

    @PostMapping("/registrarPersona")
    public ResponseEntity<String> registrarPersona(HttpServletRequest request, @Validated Persona persona) {

        if (personaService.validarCI(persona.getCi()) == null) {
            Persona persona_ = new Persona();
            persona_.setCi(persona.getCi());
            persona_.setCorreo(persona.getCorreo());
            persona_.setGenero(persona.getGenero());
            persona_.setNombre(persona.getNombre());
            persona_.setPaterno(persona.getPaterno());
            persona_.setMaterno(persona.getMaterno());
            persona_.setEstado("ACTIVO");
           personaService.save(persona_);
           return ResponseEntity.ok("Se guardó el registro con éxito");
        }else{
            return ResponseEntity.ok("Ya existe este registro");
        }
    }

    @PostMapping("/editarPersona")
    public ResponseEntity<String> editarPersona(@Validated Persona persona) {
        Persona personaExistente = personaService.findById(persona.getIdPersona());
        if (personaExistente != null) {
            personaExistente.setCi(persona.getCi());
            personaExistente.setCorreo(persona.getCorreo());
            personaExistente.setGenero(persona.getGenero());
            personaExistente.setNombre(persona.getNombre());
            personaExistente.setPaterno(persona.getPaterno());
            personaExistente.setMaterno(persona.getMaterno());
            personaExistente.setEstado("ACTIVO");
            
            personaService.save(personaExistente);
            return ResponseEntity.ok("Se modificó el registro con éxito");
        } else {
            return ResponseEntity.badRequest().body("No se encontró la persona a editar.");
        } 
    }

    @PostMapping("/listarRegistroPersona")
    public String ListarNacionalidad(HttpServletRequest request, Model model) {

        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll()); // Lista de colegios
        model.addAttribute("sexos", sexoService.findAll());
        return "Persona/tabla-registroPersona";
    }

    @PostMapping(value = "/eliminar-persona/{idPersona}")
    public ResponseEntity<String> eliminarPersona(@PathVariable("idPersona") Long idPersona) {
        personaService.deleteById(idPersona);
        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }
}
