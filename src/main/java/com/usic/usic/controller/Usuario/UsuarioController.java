package com.usic.usic.controller.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Rol;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IRolService;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IRolService rolService;

    @GetMapping(value = "/administrar-usuario")
    public String administrarUsuario(Model model) {
        
        return "Usuario/vista-usuario";
    }

    @PostMapping("/FormularioUsuario")
    public String formularioUsuario(HttpServletRequest request, Model model) {

        model.addAttribute("usuarioss", new Usuario());
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("personas", personaService.findAll());

        return "Usuario/formulario-usuario";
    }

    @GetMapping("/formularioEditUsuario/{idUsuario}")
    public String FormularioEditUsuario(HttpServletRequest request, Model model,
            @PathVariable("idUsuario") Long idUsuario) {

                Usuario usuario = usuarioService.findById(idUsuario);
                List<Rol> roles = rolService.findAll(); // Asegúrate de tener un método que devuelva todos los roles
                List<Persona> personas = personaService.findAll();
                model.addAttribute("usuarioss", usuario);
                model.addAttribute("roles", roles);
                model.addAttribute("personas", personas);
        model.addAttribute("edit", "true");
        return "Usuario/formulario-usuario";
    }

    @PostMapping("/listarUsuarioEstudiante")
    public String ListarUsuarioEstudiante(HttpServletRequest request, Model model) {

        model.addAttribute("usuarios", usuarioService.findAll());

        return "Usuario/tabla-usuario-estudiante";
    }

    @PostMapping("/listarUsuario")
    public String ListarUsuario(HttpServletRequest request, Model model) {

        model.addAttribute("usuarios", usuarioService.findAll());

        return "Usuario/tabla-usuario";
    }

    @PostMapping("/registrarUsuario")
    public ResponseEntity<String> RegistrarUsuario(HttpServletRequest request, @Validated Usuario usuario) {

        if (usuarioService.buscarPorUsuario(usuario.getUsername())  == null) {
            usuario.setPassword(usuario.getPassword());
            usuario.setRol(usuario.getRol());
            usuario.setPersona(usuario.getPersona());
            usuario.setEstado("ACTIVO");
            usuarioService.save(usuario);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        } else {
            return ResponseEntity.ok("Ya existe este registro");
        }
    }

    @PostMapping("/editarUsuario")
    public ResponseEntity<String> editarUsuario(@Validated Usuario usuario) {
        if (usuarioService.buscarPorUsuario(usuario.getUsername()) == null) {
            usuario.setEstado("ACTIVO");
            usuarioService.save(usuario);
            return ResponseEntity.ok("Se modificó el registro con éxito");
        }else{
            return ResponseEntity.ok("ya existe este usuario");
        }
    }

    @PostMapping("/eliminarUsuario/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(HttpServletRequest request,
            @PathVariable("idUsuario") Long idUsuario) {

        usuarioService.deleteById(idUsuario);

        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }

}
