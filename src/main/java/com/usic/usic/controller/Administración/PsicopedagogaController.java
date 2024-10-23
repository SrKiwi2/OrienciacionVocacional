package com.usic.usic.controller.Administración;

import java.time.LocalDate;
import java.util.List;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.IResultadoIaService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PsicopedagogaController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private ITipoTestService tipoTestService;

    @Autowired
    private IResultadoIaService resultadoIaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IGeneroService generoService;
    
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

    @GetMapping(value = "/estudiantes/{idEstudiante}")
    public ResponseEntity<String> administracionEstudianteVistaPersona(@PathVariable Long idEstudiante, Model model) {

        Estudiante estudiante = estudianteService.findById(idEstudiante);
        // List<ResultadoIA> resultados = resultadoIaService.findByEstudiante(estudiante);
        List<ResultadoIA> chasideResultados = resultadoIaService.findByEstudianteAndTipoTest(estudiante, 1L);
        List<ResultadoIA> habisociaResultados = resultadoIaService.findByEstudianteAndTipoTest(estudiante, 2L);
        List<ResultadoIA> intemultResultados = resultadoIaService.findByEstudianteAndTipoTest(estudiante, 3L);
        List<ResultadoIA> inteprofResultados = resultadoIaService.findByEstudianteAndTipoTest(estudiante, 4L);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());

        model.addAttribute("CHASIDE", chasideResultados.isEmpty() ? "" : chasideResultados.get(0).getResultado());
        model.addAttribute("HABISOCIA", habisociaResultados.isEmpty() ? "" : habisociaResultados.get(0).getResultado());
        model.addAttribute("INTEMULT", intemultResultados.isEmpty() ? "" : intemultResultados.get(0).getResultado());
        model.addAttribute("INTEPROF", inteprofResultados.isEmpty() ? "" : inteprofResultados.get(0).getResultado());
        return ResponseEntity.ok("ok");
    }
}
