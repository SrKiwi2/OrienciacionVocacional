package com.usic.usic.controller.Administración;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.InformePsicopedagoga;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.IServiceImpl.InformePsicopedagogicoServiceImpl;
import com.usic.usic.model.Repository.Sp_resultado;
import com.usic.usic.model.Service.ICarreraService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.ITipoTestService;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PsicopedagogaController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private ITipoTestService tipoTestService;
    @Autowired
    private ICarreraService carreraService;

    @Autowired
    private IUsuarioService iUsuarioService;

    @Autowired
    private InformePsicopedagogicoServiceImpl informePsicopedagogicoServiceImpl;

    @Autowired
    private Sp_resultado sp_resultado;
    
    @GetMapping("/vista_psicopedagoga")
    public String vistaPsicopedagoga(Model model) {

        model.addAttribute("num_test_chaside", sp_resultado.ObtenerNumeroEstudiantesTerminados(1L));
        model.addAttribute("num_test_sociales", sp_resultado.ObtenerNumeroEstudiantesTerminados(2L));
        model.addAttribute("num_test_profesionales", sp_resultado.ObtenerNumeroEstudiantesTerminados(3L));
        model.addAttribute("num_test_multiples", sp_resultado.ObtenerNumeroEstudiantesTerminados(4L));
        model.addAttribute("num_estudiantes_totales", sp_resultado.ObtenerEstudiantesTotales());
        
        return "Administracion/psicopedagoga/vista_psicopedagoga";
    }

    @GetMapping("/vista_configuracion")
    public String configuracionTest(Model model) {
        return "Administracion/psicopedagoga/vista_configuracion_test";
    }

    @PostMapping("/listarSeguimiento")
    public String ListarSeguimiento(HttpServletRequest request, Model model) {
        List<Estudiante> estudiantes = estudianteService.findAllOrdered();
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("totalRegistros", estudiantes.size());
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

    @PostMapping("/guardarInforme")
    public String guardarInforme(@ModelAttribute InformePsicopedagoga informePsicopedagoga, 
                                @RequestParam("l_carrera") Long[] carrerasIds,
                                @RequestParam("habilidadesSociales") String habilidadesSociales,
                                @RequestParam("estiloAprendizaje") String estiloAprendizaje,
                                @RequestParam("coeficienteIntelectual") String coeficienteIntelectual,
                                @RequestParam("conclusion") String conclusion,
                                @RequestParam("idEstudiante") Long idEstudiante,
                                @RequestParam("fechaEntrega") Date fechaEntrega,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Estudiante estudiante = estudianteService.findById(idEstudiante);
        informePsicopedagoga.setEstudiante(estudiante);
        String interpretacion = String.join(" / ", habilidadesSociales, estiloAprendizaje, coeficienteIntelectual);
        informePsicopedagoga.setFechaEntrega(fechaEntrega);
        informePsicopedagoga.setInterpretacion(interpretacion);
        informePsicopedagoga.setConclusion(conclusion);
        informePsicopedagoga.setRegistroIdUsuario(usuario.getIdUsuario());
        informePsicopedagoga.setModificacionIdUsuario(usuario.getIdUsuario());
        informePsicopedagogicoServiceImpl.save(informePsicopedagoga);

        for (int i = 0; i < carrerasIds.length; i++) {
            carreraService.insertInformeCarrera(informePsicopedagoga.getIdInformePsicopedagoga(), carrerasIds[i]);
        }

        return "redirect:/reporte_test/"+informePsicopedagoga.getIdInformePsicopedagoga();
    }

    @GetMapping(value = "/reporte_test/{id_informePsicopedagoga}")
    public String reporte_test(@PathVariable("id_informePsicopedagoga")Long id_informePsicopedagoga, Model model) {
        InformePsicopedagoga informePsicopedagoga = informePsicopedagogicoServiceImpl.findById(id_informePsicopedagoga);
        model.addAttribute("informePsicopedagoga", informePsicopedagoga);
        Date fecha_de_nacimiento = informePsicopedagoga.getEstudiante().getPersona().getFecha();
        LocalDate fechaNacimientoLocalDate = fecha_de_nacimiento.toInstant()
                                                                .atZone(ZoneId.systemDefault())
                                                                .toLocalDate();
        int edad = Period.between(fechaNacimientoLocalDate, LocalDate.now()).getYears();
        model.addAttribute("edad", edad);
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fecha.format(formatter);
        model.addAttribute("fecha_generado", fechaFormateada);
        model.addAttribute("intepretaciones", sp_resultado.ObtenerInterpretaciones(id_informePsicopedagoga));
        model.addAttribute("lista_carreras", sp_resultado.ObtenerCarrerasXInforme(id_informePsicopedagoga));

        Usuario usuario = iUsuarioService.findById(informePsicopedagoga.getRegistroIdUsuario());
        model.addAttribute("evaluador", usuario.getPersona().getNombre()+" "+usuario.getPersona().getPaterno()+" "+usuario.getPersona().getMaterno());

        return "test/resultado_tests/reporte_resultados";
    }
}