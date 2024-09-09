package com.usic.usic.controller.Sexo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Entity.Genero;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GeneroController {
    
    @Autowired
    private IGeneroService sexoService;

    @GetMapping(value = "/administrar-sexo")
    public String administrarSexo(Model model) {

        return "Complementos/Sexo/vista-sexo";
    }

    @PostMapping("/FormularioSexo")
    public String formularioSexo(HttpServletRequest request, Model model) {

        model.addAttribute("sexoss", new Genero());

        return "Complementos/Sexo/formulario-sexo";
       
    }

    @GetMapping("/formularioEditSexo/{idSexo}")
    public String FormularioEditSexo(HttpServletRequest request, Model model,
            @PathVariable("idSexo") Long idSexo) {

        model.addAttribute("sexoss", sexoService.findById(idSexo));
        model.addAttribute("edit", "true");
        return "Complementos/Sexo/formulario-sexo";
    }

    @PostMapping("/listarSexo")
    public String ListarSexo(HttpServletRequest request, Model model) {

        model.addAttribute("sexos", sexoService.findAll());

        return "Complementos/Sexo/tabla-sexo";
    }

    @PostMapping("/registrarSexo")
    public ResponseEntity<String> RegistrarSexo(HttpServletRequest request, @Validated Genero sexo) {
        System.out.println("Sexo recibido: " + sexo.getGenero());
        if (sexoService.buscarPorGenero(sexo.getGenero())  == null) {
            sexo.setEstado("ACTIVO");
            sexoService.save(sexo);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        } else {
            return ResponseEntity.ok("Ya existe este registro");
        }

    }

    @PostMapping("/editarSexo")
    public ResponseEntity<String> editarSexo(@Validated Genero sexo) {
        if (sexoService.buscarPorGenero(sexo.getGenero()) == null) {
            sexo.setEstado("ACTIVO");
            sexoService.save(sexo);
            return ResponseEntity.ok("Se modificó el registro con éxito");
        }else{
            return ResponseEntity.ok("ya existe este colegio");
        }
    }

    @PostMapping("/eliminarSexo/{idSexo}")
    public ResponseEntity<String> eliminarSexo(HttpServletRequest request,
            @PathVariable("idSexo") Long idSexo) {

                sexoService.deleteById(idSexo);

        return ResponseEntity.ok("Se eliminó el registro con éxito");
    }
}
