package com.usic.usic.controller.Administración;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.IService.IRolService;
import com.usic.usic.model.entity.Colegio;
import com.usic.usic.model.entity.Rol;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RolController {
    
    @Autowired
    private IRolService rolService;

    @GetMapping(value = "/vista-rol")
    public String vistaRol(Model model) {
        return "/Administracion/Rol/admin-rol";
    }

    @PostMapping("/FormularioRol")
    public String formularioRol(HttpServletRequest request, Model model) {

        model.addAttribute("rolss", new Rol());
        return "Administracion/Rol/form-rol";  
    }

    @GetMapping("/formularioEditRol/{idRol}")
    public String FormularioEditRol(HttpServletRequest request, Model model,
            @PathVariable("idRol") Long idRol) {

        model.addAttribute("rolss", rolService.findById(idRol));
        model.addAttribute("edit", "true");
        return "Administracion/Rol/form-rol";
    }

    @PostMapping("/listarRol")
    public String ListarRol(HttpServletRequest request, Model model) {

        model.addAttribute("roles", rolService.findAll());
        return "Administracion/Rol/tabla-rol";
    }

    @PostMapping("/registrarRol")
    public ResponseEntity<String> RegistrarRol(HttpServletRequest request, @Validated Rol rol) {

        if (rolService.buscarPorNombre(rol.getNombre())  == null) {
            rol.setEstado("ACTIVO");
            rolService.save(rol);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        } else {
            return ResponseEntity.ok("Ya existe este registro");
        }
    }

    @PostMapping("/editarRol")
    public ResponseEntity<String> editarRol(@Validated Rol rol) {
        if (rolService.buscarPorNombre(rol.getNombre()) == null) {
            rol.setEstado("ACTIVO");
            rolService.save(rol);
            return ResponseEntity.ok("Se modificó el registro con éxito");
        }else{
            return ResponseEntity.ok("ya existe este rol");
        }
    }

    @PostMapping("/eliminarRol/{idRol}")
    public ResponseEntity<String> eliminarRol(HttpServletRequest request,
            @PathVariable("idRol") Long idRol) {

        rolService.deleteById(idRol);

        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }
}
