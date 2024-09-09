package com.usic.usic.controller.Colegio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.IService.IColegioService;
import com.usic.usic.model.entity.Colegio;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ColegioController {
    
    @Autowired
    private IColegioService colegioService;

    @GetMapping(value = "/administrar-colegio")
    public String administrarColegio(Model model) {
        // model.addAttribute("colegios", new Colegio()); 
        // model.addAttribute("listaColegios", colegioService.findAll());
        return "Complementos/Colegio/vista-colegio";
    }

    @PostMapping("/FormularioColegio")
    public String formularioColegio(HttpServletRequest request, Model model) {

        model.addAttribute("colegioss", new Colegio());

        return "Complementos/Colegio/formulario-colegio";
       
    }

    @GetMapping("/formularioEditColegio/{idColegio}")
    public String FormularioEditColegio(HttpServletRequest request, Model model,
            @PathVariable("idColegio") Long idColegio) {

        model.addAttribute("colegioss", colegioService.findById(idColegio));
        model.addAttribute("edit", "true");
        return "Complementos/Colegio/formulario-colegio";
    }

    @PostMapping("/listarColegio")
    public String ListarColegio(HttpServletRequest request, Model model) {

        model.addAttribute("colegios", colegioService.findAll());

        return "Complementos/Colegio/tabla-colegio";
    }

    @PostMapping("/registrarColegio")
    public ResponseEntity<String> RegistrarColegio(HttpServletRequest request, @Validated Colegio colegio) {

        if (colegioService.buscarColegio(colegio.getNombreColegio())  == null) {
            colegio.setEstado("ACTIVO");
            colegioService.save(colegio);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        } else {
            return ResponseEntity.ok("Ya existe este registro");
        }

    }

    @PostMapping("/editarColegio")
    public ResponseEntity<String> editarColegio(@Validated Colegio colegio) {
        if (colegioService.buscarColegio(colegio.getNombreColegio()) == null) {
            colegio.setEstado("ACTIVO");
            colegioService.save(colegio);
            return ResponseEntity.ok("Se modificó el registro con éxito");
        }else{
            return ResponseEntity.ok("ya existe este colegio");
        }
    }

    @PostMapping("/eliminarColegio/{idColegio}")
    public ResponseEntity<String> eliminarColegio(HttpServletRequest request,
            @PathVariable("idColegio") Long idColegio) {

        colegioService.deleteById(idColegio);

        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }
}
