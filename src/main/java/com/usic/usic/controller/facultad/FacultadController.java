package com.usic.usic.controller.facultad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usic.usic.anotaciones.ValidarUsuarioAutenticado;
import com.usic.usic.model.Entity.Facultad;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IFacultadService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/facultad")
public class FacultadController {

    @Autowired
    private IFacultadService facultadService;

    @ValidarUsuarioAutenticado
    @GetMapping("/vista")
    public String inicio() {
        return "facultad/vista";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/tabla-registros")
    public String tablaRegistros(Model model) throws Exception {
        List<Facultad> listaFacultades = facultadService.listarFacultades();
        model.addAttribute("listaFacultades", listaFacultades);
        return "facultad/tabla-registro";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/formulario")
    public String formulario(Model model, Facultad facultad) {
        
        return "facultad/formulario";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/formulario-edit/{id_facultad}")
    public String formularioEdit(Model model, @PathVariable("id_facultad") String idFacultad) throws Exception{
        Long id = Long.parseLong(idFacultad);
        model.addAttribute("facultad", facultadService.findById(id));
        model.addAttribute("edit", "true");
        return "facultad/formulario";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/registrar-facultad")
    public ResponseEntity<String> RegistrarFacultad(HttpServletRequest request, @Validated Facultad facultad) {
        if (facultadService.buscarFacultadPorNombre(facultad.getFacultad()) == null) {
            facultad.setEstado("ACTIVO");
            facultadService.save(facultad);
            return ResponseEntity.ok("Se realizó el registro correctamente");
        } else {
            return ResponseEntity.ok("Ya existe un rol con este nombre");
        }
    }

    @PostMapping(value = "/modificar-facultad")
    public ResponseEntity<String> modificar(HttpServletRequest request, Facultad facultad,
            RedirectAttributes redirectAttrs) {
        
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        facultad.setModificacionIdUsuario(usuario.getIdUsuario());
        facultad.setEstado("ACTIVO");
        facultadService.save(facultad);

        return ResponseEntity.ok("Se realizó el registro correctamente");

    }

    @ValidarUsuarioAutenticado
    @PostMapping("/eliminar/{id_facultad}")
    public ResponseEntity<String> eliminar(Model model, @PathVariable("id_facultad") String idFacultad) throws Exception {
        Long id = Long.parseLong(idFacultad);
        Facultad facultad = facultadService.findById(id);
        facultad.setEstado("ELIMINADO");
        facultadService.save(facultad);
        return ResponseEntity.ok("Registro Eliminado");
    }
}
