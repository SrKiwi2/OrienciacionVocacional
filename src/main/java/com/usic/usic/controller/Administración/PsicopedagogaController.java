package com.usic.usic.controller.Administración;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PsicopedagogaController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private ITipoTestService tipoTestService;
    
    @GetMapping("/vista_psicopedagoga")
    public String vistaPsicopedagoga() {
        return "Administracion/psicopedagoga/vista_psicopedagoga";
    }

    @GetMapping("/vista_configuracion")
    public String configuracionTest(Model model) {
        return "Administracion/psicopedagoga/vista_configuracion_test";
    }

    @PostMapping("/listarSeguimiento")
    public String ListarSeguimiento(HttpServletRequest request, Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "Administracion/psicopedagoga/tabla_seguimiento";
    }

    @PostMapping("/listarConfiguracionTest")
    public String listarConfiguracionTest(HttpServletRequest request, Model model) {
        model.addAttribute("tests", tipoTestService.findAll());
        return "Administracion/psicopedagoga/tabla_test";
    }

    @PostMapping("/habilitarTest")
    public ResponseEntity<String> habilitarTest(@RequestParam Long id_tipo_test,
                                                @RequestParam LocalDate fechaInicio,
                                                @RequestParam LocalDate fechaFin) {
        try {
            TipoTest test = tipoTestService.findById(id_tipo_test);
            test.setFechaInicio(fechaInicio);
            test.setFechaFin(fechaFin);
            tipoTestService.save(test);
            return ResponseEntity.ok("Las fechas para este test han sido establecidas con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al establecer las fechas del test.");
        }
    }

}
