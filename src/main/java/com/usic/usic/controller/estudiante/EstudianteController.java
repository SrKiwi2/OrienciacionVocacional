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

        // Verificar si el CI ya existe
        Persona existingPersonaByCi = personaService.validarCI(persona.getCi());
        if (existingPersonaByCi != null) {
            return ResponseEntity.badRequest().body("El CI ya está registrado.");
        }

        // Verificar si el correo electrónico ya existe
        Persona existingPersonaByEmail = personaService.findByCorreo(persona.getCorreo());
        if (existingPersonaByEmail != null) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }

        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setPaterno(persona.getPaterno().toUpperCase());
        persona.setMaterno(persona.getMaterno().toUpperCase());
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
    public String guardar_estudiante_inicio(@Validated Persona persona,
                                            @RequestParam("grado") String grado,
                                            @RequestParam("colegio") Long idColegio, 
                                            Model model,HttpServletRequest request
                                            ,RedirectAttributes flash) {

        // Verificar si el CI ya existe
        Persona existingPersonaByCi = personaService.validarCI(persona.getCi());
        if (existingPersonaByCi != null) {
            System.out.println("este CI ya Existe");
            return "redirect:/";
        }

        // Verificar si el correo electrónico ya existe
        Persona existingPersonaByGmail = personaService.findByCorreo(persona.getCorreo());
        if (existingPersonaByGmail != null) {
            System.out.println("Este Gmail ya Existe");
            return "redirect:/";
        }

        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setPaterno(persona.getPaterno().toUpperCase());
        persona.setMaterno(persona.getMaterno().toUpperCase());
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

        return "redirect:/pre_test"; //aqui va al pre test, parapresnetar lo cambie---- k
    }

    
    @PostMapping("/estudiante/habilitar/{idEstudiante}")
    public ResponseEntity<?> habilitarEstudiante(@PathVariable Long idEstudiante) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);
        
        if (estudiante != null) {
            estudiante.setEstado("HABILITADO"); 
            estudianteService.save(estudiante); 

            Persona persona = estudiante.getPersona();
            if (persona != null) {
                Usuario usuario = persona.getUsuario();
                if (usuario != null) {
                    usuario.setEstado("E");

                    String asunto = "CREDENCIAL DE ACCESO";
                    String cuerpo = "Estimado/a "+ persona.getNombre() +",\n\nTus credenciales de acceso al sistema de orientacion Vocacional son:\nUsuario: "+ persona.getPaterno() + "\nContraseña: " + persona.getCi()+"_uap" + "\n\nGracias.";
                    enviarEmail.enviarCorreo(persona.getCorreo(), asunto, cuerpo);

                    usuarioService.save(usuario);
                    return ResponseEntity.ok().body("Usuario de estudiante habilitado con éxito.");
                }else{
                    return ResponseEntity.ok().body("Estudiante habilitado, pero no se encontró un usuario asociado.");
                }
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una persona asociada al estudiante.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado.");
        } 
    }


























    @GetMapping(value = "regEstudiante")
    public String getEstudiante(Model model) {

        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("estudiante", new Estudiante());

        return "test/";
    }
    

    // Colegio colegio = colegioService.findById(idColegio);
    //     if (colegio == null) {
    //         colegio = new Colegio();
    //     }
}
