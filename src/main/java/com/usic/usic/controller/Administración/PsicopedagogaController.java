package com.usic.usic.controller.Administración;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileOutputStream;

import com.itextpdf.text.Document; 
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.usic.usic.model.Entity.Carrera;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Facultad;
import com.usic.usic.model.Entity.InformePsicopedagoga;
import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.IServiceImpl.InformePsicopedagogicoServiceImpl;
import com.usic.usic.model.Repository.Sp_resultado;
import com.usic.usic.model.Service.ICarreraService;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IFacultadService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.IResultadoIaService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PsicopedagogaController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private ITipoTestService tipoTestService;

    @Autowired
    private IFacultadService facultadService;

    @Autowired
    private ICarreraService carreraService;

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

        System.out.println(sp_resultado.ObtenerEstudiantesTotales());


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
                                @RequestParam("facultad") List<Long> facultadesIds, 
                                @RequestParam("carrera") List<Long> carrerasIds, 
                                @RequestParam("chaside") String chaside,
                                @RequestParam("habilidadesSociales") String habilidadesSociales,
                                @RequestParam("inteligenciasMultiples") String inteligenciasMultiples,
                                @RequestParam("interesesProfesionales") String interesProfesionales,
                                @RequestParam("conclusion") String conclusion,
                                @RequestParam("idEstudiante") Long idEstudiante,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        // Asigna la Facultad y Carrera a la entidad
        Facultad facultad = facultadService.findById(facultadesIds.get(0)); // puedes ajustar para manejar múltiples facultades
        Carrera carrera = carreraService.findById(carrerasIds.get(0)); // puedes ajustar para manejar múltiples carreras

        Estudiante estudiante = estudianteService.findById(idEstudiante);
        informePsicopedagoga.setEstudiante(estudiante);
        informePsicopedagoga.setFacultad(facultad);
        informePsicopedagoga.setCarrera(carrera);
        String interpretacion = String.join(" / ", chaside, habilidadesSociales, inteligenciasMultiples, interesProfesionales);
        informePsicopedagoga.setInterpretacion(interpretacion);
        informePsicopedagoga.setConclusion(conclusion);
        informePsicopedagoga.setRegistroIdUsuario(usuario.getIdUsuario());
        informePsicopedagoga.setModificacionIdUsuario(usuario.getIdUsuario());
        informePsicopedagogicoServiceImpl.save(informePsicopedagoga);

        System.out.println("Todo bien maestro, en teoria se guardó");
        // Generar el PDF
        generarPDF(informePsicopedagoga);

        redirectAttributes.addFlashAttribute("message", "Informe guardado y PDF generado exitosamente");
        return "redirect:/ruta_formulario";
    }

    private void generarPDF(InformePsicopedagoga informe) {
        String nombreArchivo = "Informe_" + informe.getEstudiante().getPersona().getNombre() + ".pdf";
        
        System.out.println("Jefe, mostrando PDF!!");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();
            
            // Agregar contenido al PDF
            document.add(new Paragraph("Informe Psicopedagógico"));
            document.add(new Paragraph("Nombre del Estudiante: " + informe.getEstudiante().getPersona().getNombre()));
            document.add(new Paragraph("Facultad: " + informe.getFacultad().getFacultad()));
            document.add(new Paragraph("Carrera: " + informe.getCarrera().getCarrera()));
            document.add(new Paragraph("Interpretación: " + informe.getInterpretacion()));
            document.add(new Paragraph("Conclusión: " + informe.getConclusion()));
            
            // Cerrar el documento
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
