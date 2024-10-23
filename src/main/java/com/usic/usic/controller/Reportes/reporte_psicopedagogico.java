package com.usic.usic.controller.Reportes;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.IResultadoIaService;
import com.usic.usic.model.Service.ITipoTestService;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class reporte_psicopedagogico {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IResultadoIaService resultadoIaService;

    @Autowired
    private ITipoTestService tipoTestService;
    
    @GetMapping("/estudianteEvaluacion/{idEstudiante}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long idEstudiante, Model model) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);

        model.addAttribute("estudiante", estudiante);

        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/datosEstudianteSeguimiento/{idEstudiante}")
    public String datosEstudianteSeguimiento(@PathVariable("idEstudiante") Long idEstudiante, Model model, HttpServletRequest request) {
        Persona persona = (Persona) request.getSession().getAttribute("persona");
        String PersonaUsuario = persona.getNombre() + ' ' + persona.getPaterno() + ' ' + persona.getMaterno();
        Estudiante estudiante = estudianteService.findById(idEstudiante);

        List<ResultadoIA> resultados = resultadoIaService.findByEstudiante(estudiante);

        // Dividimos los resultados que contienen '/'
        for (ResultadoIA resultado : resultados) {
            if (resultado.getResultado() != null) {
                String[] partesResultado = resultado.getResultado().split("/");
                resultado.setResultado(String.join("</p><p>", partesResultado));
            }
        }

        // Extraer los tipos de test de los resultados
        Set<TipoTest> tiposTest = resultados.stream()
                                        .map(ResultadoIA::getTipoTest)
                                        .collect(Collectors.toSet());

        System.out.println(estudiante);
        // Calcular la edad
        Date fechaNacimientoDate = estudiante.getPersona().getFecha();
        LocalDate fechaNacimiento = fechaNacimientoDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        LocalDate fechaActual = LocalDate.now();
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("genero", generoService.findAll());
        model.addAttribute("edad", edad);
        model.addAttribute("colegio", colegioService.findAll());
        model.addAttribute("usuario", PersonaUsuario);
        model.addAttribute("fechaActual", fechaActual);
        model.addAttribute("resultados", resultados);
        model.addAttribute("tiposTest", tiposTest);

        return "Administracion/psicopedagoga/form_seguiminto";
    }

    
}
