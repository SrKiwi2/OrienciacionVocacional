package com.usic.usic.controller.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Genero;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Rol;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEnviarEmail;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.Service.IRolService;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EstudianteController {

    @Autowired
    private IEstudianteService estudianteService;

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

    @GetMapping(value = "/vista-estudiantes")
    public String vistaPersona(Model model) {
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("colegios", colegioService.findAll());
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("estudiantes", estudianteService.findAll());
        model.addAttribute("persona", new Persona());
        return "Estudiante/vista_estudiante";
    }

    @PostMapping("/listarEstudiantesInicio")
    public String listarEstudiantesInicio(HttpServletRequest request, Model model) {

        model.addAttribute("estudiantes", estudianteService.findAll());

        return "Estudiante/admin-estudiantes/tabla-adminEstudiantes";
    }

    @PostMapping(value = "/guardar-estudiante")
    public String guardarPersona(
        @RequestParam(value = "idPersona", required = false) Long idPersona,
        @RequestParam("nombre") String nombre,
        @RequestParam("paterno") String paterno,
        @RequestParam("materno") String materno,
        @RequestParam("ci") String ci,
        @RequestParam("correo") String correo,
        @RequestParam("idGenero") Long idGenero,
        @RequestParam("idColegio") Long idColegio,
        @RequestParam("grado") String grado,
        Model model) {

        Persona persona = personaService.validarCI(ci);
        if (persona == null) {
            persona = new Persona();
        }
        persona.setCi(ci);
        persona.setNombre(nombre);
        persona.setPaterno(paterno);
        persona.setMaterno(materno);
        persona.setCorreo(correo);
        persona.setEstado("E");

        Genero genero = generoService.findById(idGenero);
        if (genero == null) {
            genero = new Genero();
            persona.setGenero(genero);
        }
        persona.setGenero(genero);

        personaService.save(persona);

        Estudiante estudiante = estudianteService.findById(persona.getIdPersona());
        if (estudiante == null) {
            estudiante = new Estudiante();
            estudiante.setPersona(persona);

            Colegio colegio = colegioService.findById(idColegio);
            estudiante.setColegio(colegio);
            estudiante.setGrado(grado);
            estudiante.setEstado("INHABILITADO");
            estudianteService.save(estudiante);
        }

        Usuario usuarioEstudiante = usuarioService.findByPersona(persona);
        if (usuarioEstudiante == null) {
            usuarioEstudiante = new Usuario();
            usuarioEstudiante.setPersona(persona);
            String nombreUsuario = paterno.toLowerCase();
            usuarioEstudiante.setUsername(nombreUsuario);

            String contrasena = ci + "_uap";
            // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // String contrasenaEncriptada = passwordEncoder.encode(contrasena);

            usuarioEstudiante.setPassword(contrasena);

            usuarioEstudiante.setEstado("INHABILITADO");
            usuarioService.save(usuarioEstudiante);
            
            Rol rolEstudiante = rolService.buscarPorNombre("ESTUDIANTES");
            if (rolEstudiante != null) {
                usuarioEstudiante.setRol(rolEstudiante);
            }
            usuarioService.save(usuarioEstudiante);

            String asunto = "CREDENCIAL DE ACCESO";
            String cuerpo = "Estimado/a "+ nombre +",\n\nTus credenciales de acceso al sistema de orientacion Vocacional son:\nUsuario: "+ nombreUsuario + "\nContraseña: " + contrasena + "\n\nGracias.";
            enviarEmail.enviarCorreo(correo, asunto, cuerpo);
        }else{
            System.out.println("Ya existe este usuario y contraseña para este estudiante");
        }

        return "redirect:/vista-estudiantes";
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
                    usuario.setEstado("ACTIVO");
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
