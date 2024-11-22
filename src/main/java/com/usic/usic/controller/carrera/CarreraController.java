package com.usic.usic.controller.carrera;

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
import com.usic.usic.model.Entity.Carrera;
import com.usic.usic.model.Entity.Facultad;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.ICarreraService;
import com.usic.usic.model.Service.IFacultadService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/carrera")
public class CarreraController {

    @Autowired
    private ICarreraService carreraService;

    @Autowired
    private IFacultadService facultadService;
    
    @ValidarUsuarioAutenticado
    @GetMapping("/vista")
    public String inicio() {
        return "carrera/vista";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/tabla-registros")
    public String tablaRegistros(Model model) throws Exception {
        List<Carrera> listaCarreras = carreraService.listarCarreras();
        model.addAttribute("listaCarreras", listaCarreras);
        return "carrera/tabla-registro";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/formulario")
    public String formulario(Model model, Carrera carrera) {
        model.addAttribute("facultades", facultadService.listarFacultades());
        return "carrera/formulario";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/formulario-edit/{id_carrera}")
    public String formularioEdit(Model model, @PathVariable("id_carrera") String idCarrera) throws Exception{
        Long id = Long.parseLong(idCarrera);
        model.addAttribute("carrera", carreraService.findById(id));
        model.addAttribute("edit", "true");
        return "carrera/formulario";
    }

    @ValidarUsuarioAutenticado
    @PostMapping("/registrar-carrera")
    public ResponseEntity<String> RegistrarCarrera(HttpServletRequest request, @Validated Carrera carrera) {
        System.out.println("!!!!");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        System.out.println(carrera.getFacultad());
            carrera.setRegistroIdUsuario(usuario.getIdUsuario());
            carrera.setEstado("ACTIVO");
            carreraService.save(carrera);
            return ResponseEntity.ok("Se realizó el registro correctamente");
    }

    @PostMapping(value = "/modificar-carrera")
    public ResponseEntity<String> modificar(HttpServletRequest request, Carrera carrera,
            RedirectAttributes redirectAttrs) {
        
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        carrera.setModificacionIdUsuario(usuario.getIdUsuario());
        carrera.setEstado("ACTIVO");

        Long idFacultad = carrera.getFacultad().getIdFacultad();
        Facultad facultad = facultadService.findById(idFacultad);
        System.out.println("Facultad: "+facultad);
        carrera.setFacultad(facultad);
        carreraService.save(carrera);
        return ResponseEntity.ok("Se realizó el registro correctamente");

    }

    @ValidarUsuarioAutenticado
    @PostMapping("/eliminar/{id_carrera}")
    public ResponseEntity<String> eliminar(Model model, @PathVariable("id_carrera") String idCarrera) throws Exception {
        Long id = Long.parseLong(idCarrera);
        Carrera carrera = carreraService.findById(id);
        carrera.setEstado("ELIMINADO");
        carreraService.save(carrera);
        return ResponseEntity.ok("carreragistro Eliminado");
    }
}
