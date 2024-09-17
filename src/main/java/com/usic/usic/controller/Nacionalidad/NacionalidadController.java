package com.usic.usic.controller.Nacionalidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Nacionalidad;
import com.usic.usic.model.Service.INacionalidadService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NacionalidadController {

    @Autowired
    private INacionalidadService nacionalidadService;
    


    @GetMapping(value = "/administrar-nacionalidad")
    public String administrarNacionalidad(Model model) {
        // model.addAttribute("colegios", new Colegio()); 
        // model.addAttribute("listaColegios", colegioService.findAll());
        return "Complementos/Nacionalidad/vista-nacionalidad";
    }

    @PostMapping("/FormularioNacionalidad")
    public String formularioNacionalidad(HttpServletRequest request, Model model) {

        model.addAttribute("nacionalidadess", new Nacionalidad());

        return "Complementos/Nacionalidad/formulario-nacionalidad";
       
    }

    @GetMapping("/formularioEditNacionalidad/{idNacionalidad}")
    public String FormularioEditColegio(HttpServletRequest request, Model model,
            @PathVariable("idNacionalidad") Long idNacionalidad) {

        model.addAttribute("nacionalidadess", nacionalidadService.findById(idNacionalidad));
        model.addAttribute("edit", "true");
        return "Complementos/Nacionalidad/formulario-nacionalidad";
    }

    @PostMapping("/listarNacionalidad")
    public String ListarNacionalidad(HttpServletRequest request, Model model) {

        model.addAttribute("nacionalidades", nacionalidadService.findAll());

        return "Complementos/Nacionalidad/tabla-nacionalidad";
    }

    @PostMapping("/registrarNacionalidad")
    public ResponseEntity<String> RegistrarNacionalidad(HttpServletRequest request, @Validated Nacionalidad nacionalidad) {

        if (nacionalidadService.buscarNacionalidad(nacionalidad.getNacionalidad())  == null) {
            nacionalidad.setEstado("ACTIVO");
            nacionalidadService.save(nacionalidad);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        } else {
            return ResponseEntity.ok("Ya existe este registro");
        }

    }

    @PostMapping("/editarNacionalidad")
    public ResponseEntity<String> editarNacionalidad(@Validated Nacionalidad nacionalidad) {
        if (nacionalidadService.buscarNacionalidad(nacionalidad.getNacionalidad()) == null) {
            nacionalidad.setEstado("ACTIVO");
            nacionalidadService.save(nacionalidad);
            return ResponseEntity.ok("Se modificó el registro con éxito");
        }else{
            return ResponseEntity.ok("ya existe este nacionalidad");
        }
    }

    @PostMapping("/eliminarNacionalidad/{idNacionalidad}")
    public ResponseEntity<String> eliminarNacionalidad(HttpServletRequest request,
            @PathVariable("idNacionalidad") Long idNacionalidad) {

        nacionalidadService.deleteById(idNacionalidad);

        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }
}
