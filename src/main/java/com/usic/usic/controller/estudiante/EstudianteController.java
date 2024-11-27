package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEnviarEmail;
import com.usic.usic.model.Service.IEstudianteRespuestaService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.INacionalidadService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IRolService;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;


@Controller
public class EstudianteController {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IEstudianteRespuestaService estudianteRespuestaService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private IEnviarEmail enviarEmail;

    @Autowired
    private INacionalidadService nacionalidadService;

    @GetMapping("/total-respuestas-si/{idEstudiante}")
    public ResponseEntity<Integer> getTotalRespuestasSi(@PathVariable Long idEstudiante) {
        int totalRespuestasSi = estudianteRespuestaService.countRespuestasSiByEstudiante(idEstudiante);
        return ResponseEntity.ok(totalRespuestasSi);
    }

    @GetMapping(value = "/vista-estudiantes")
    public String vistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("nacionalidades", nacionalidadService.findAll());
        model.addAttribute("persona", new Persona());
        return "Estudiante/registros_estudiantes/vista_estudiante";
    }

    @PostMapping("/listarEstudiantesInicio")
    public String listarEstudiantesInicio(HttpServletRequest request, Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "Estudiante/registros_estudiantes/tabla-adminEstudiantes";
    }

    @PostMapping(value = "/guardar_estudiante_adm")
    public ResponseEntity<String> guardar_estudiante_adm(@Validated Persona persona,
                                            @RequestParam("grado") String grado,
                                            @RequestParam("colegio") Long idColegio,
                                             Model model) {

        Persona existingPersonaByCi = personaService.validarCI(persona.getCi());
        if (existingPersonaByCi != null) {
            return ResponseEntity.badRequest().body("El CI ya está registrado.");
        }

        Persona existingPersonaByEmail = personaService.findByCorreo(persona.getCorreo());
        if (existingPersonaByEmail != null) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }

        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setPaterno(persona.getPaterno().toUpperCase());
        persona.setMaterno(persona.getMaterno().toUpperCase());
        persona.setFecha(persona.getFecha());
        persona.setEstado("E");
        personaService.save(persona);

        Estudiante estudiante = estudianteService.findById(persona.getIdPersona());
        if (estudiante == null) {
            estudiante = new Estudiante();
            estudiante.setPersona(persona);
            estudiante.setEstado("INHABILITADO");
        }

        estudiante.setGrado(grado);
        Colegio colegio = colegioService.findById(idColegio);
        estudiante.setColegio(colegio);
        estudianteService.save(estudiante);

        Usuario usuario = usuarioService.findById(persona.getIdPersona());

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setPersona(persona);
            usuario.setUsername(persona.getPaterno());
            usuario.setPassword(persona.getCi() + "_uap");
            usuario.setEstado("INHABILITADO");
            usuario.setRol(rolService.buscarPorNombre("ESTUDIANTES"));
            usuarioService.save(usuario);
        }                                      
        
        return ResponseEntity.ok("registrado");
    }

    @PostMapping(value = "/guardar_estudiante_inicio")
    public ResponseEntity<?> guardar_estudiante_inicio(@Validated Persona persona,
                                            @RequestParam("grado") String grado,
                                            @RequestParam("colegio") Long idColegio, 
                                            Model model,HttpServletRequest request
                                            ,RedirectAttributes flash) {
        try {

            Persona existingPersonaByCi = personaService.validarCI(persona.getCi());
            if (existingPersonaByCi != null) {
                return ResponseEntity.ok("Ya estas registrado con este ci");
            }

            Persona existingPersonaByGmail = personaService.findByCorreo(persona.getCorreo());
            if (existingPersonaByGmail != null) {
                return ResponseEntity.ok("Ya estas registrado con este correo electronico");
            }

            persona.setNombre(persona.getNombre().toUpperCase());
            persona.setPaterno(persona.getPaterno().toUpperCase());
            persona.setMaterno(persona.getMaterno().toUpperCase());
            persona.setFecha(persona.getFecha());
            persona.setEstado("E");
            personaService.save(persona);

            Estudiante estudiante = estudianteService.findById(persona.getIdPersona());
            if (estudiante == null) {
                estudiante = new Estudiante();
                estudiante.setPersona(persona);
                estudiante.setEstado("INHABILITADO");
            }

            estudiante.setGrado(grado);
            Colegio colegio = colegioService.findById(idColegio);
            estudiante.setColegio(colegio);
            estudianteService.save(estudiante);

            Usuario usuario = usuarioService.findById(persona.getIdPersona());
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setPersona(persona);
                usuario.setUsername(persona.getPaterno());
                usuario.setPassword(persona.getCi() + "_uap");
                usuario.setEstado("INHABILITADO");
                usuario.setRol(rolService.buscarPorNombre("ESTUDIANTES"));
                usuarioService.save(usuario);
            }

            HttpSession sessionAdministrador = request.getSession(true);
            sessionAdministrador.setAttribute("usuario", usuario);
            sessionAdministrador.setAttribute("persona", usuario.getPersona());
            sessionAdministrador.setAttribute("nombre_rol", usuario.getRol().getNombre());

            flash.addAttribute("pre_test_iniciado_", usuario.getPersona().getNombre());

            return ResponseEntity.ok("Redireccionando");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Ah ocurrido algo inesperado :(, vuelvo a intentar" + ex.getMessage());
        }
    }
    
    @PostMapping("/estudiante/habilitar/{idEstudiante}")
    public ResponseEntity<?> habilitarEstudiante(@PathVariable Long idEstudiante) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);
        System.out.println("accedi al metodo");
        if (estudiante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado.");
        }

        estudiante.setEstado("HABILITADO");
        estudianteService.save(estudiante);

        Persona persona = estudiante.getPersona();
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una persona asociada.");
        }

        Usuario usuario = persona.getUsuario();
        if (usuario == null) {
            return ResponseEntity.ok("Estudiante habilitado, pero no se encontró un usuario asociado.");
        }

        usuario.setEstado("E");
        usuarioService.save(usuario);

        String plantilla;

        try {
            plantilla = cargarPlantillaHTML("src/main/resources/templates/correo/send_correo.html");
        } catch (IOException e) {
            System.out.println("df");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cargar la plantilla de correo.");
        }

        String nombreCompleto = construirNombreCompleto(persona);
        
        String cuerpo = plantilla
                .replace("{{nombre}}", nombreCompleto)
                .replace("{{usuario}}", persona.getPaterno()) 
                .replace("{{password}}", persona.getCi() + "_uap");

        enviarEmail.enviarCorreo(persona.getCorreo(), "CREDENCIAL DE ACCESO", cuerpo, true);

        return ResponseEntity.ok("Usuario de estudiante habilitado con éxito.");
    }


    private String cargarPlantillaHTML(String rutaArchivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(rutaArchivo)), StandardCharsets.UTF_8);
    }

    private String construirNombreCompleto(Persona persona) {
        StringBuilder nombreCompleto = new StringBuilder(persona.getNombre());
        if (persona.getPaterno() != null && !persona.getPaterno().isEmpty()) {
            nombreCompleto.append(" ").append(persona.getPaterno());
        }
        if (persona.getMaterno() != null && !persona.getMaterno().isEmpty()) {
            nombreCompleto.append(" ").append(persona.getMaterno());
        }
        return nombreCompleto.toString();
    }
}
