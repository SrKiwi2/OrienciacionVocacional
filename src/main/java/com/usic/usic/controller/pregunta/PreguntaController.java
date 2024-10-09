package com.usic.usic.controller.pregunta;

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

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.TipoPregunta;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.Service.ITipoPreguntaService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PreguntaController {

    @Autowired
    private IPreguntaService preguntaService;

    @Autowired
    private ITipoPreguntaService tipoPreguntaService;

    @Autowired
    private ITipoTestService tipoTestService;

    @GetMapping(value = "/adm_vista_pregunta")
    public String administracionEstudianteVistaPersona(Model model) {
        return "pregunta/vista_pregunta";
    }

    @PostMapping("/listarPreguntas")
    public String listarPreguntas(HttpServletRequest request, Model model) {
        model.addAttribute("preguntas", preguntaService.findAll());
        model.addAttribute("tipoPregunta", tipoPreguntaService.findAll());
        model.addAttribute("tipoTest", tipoTestService.findAll());
        return "pregunta/tabla_pregunta";
    }

    @PostMapping("/FormularioPregunta")
    public String formularioPregunta(HttpServletRequest request, Model model) {
        model.addAttribute("tipoPregunta", tipoPreguntaService.findAll());
        model.addAttribute("tipoTest", tipoTestService.findAll());
        model.addAttribute("preguntass", new Pregunta());
        return "pregunta/formulario_pregunta";
    }

    @GetMapping("/formularioEditPregunta/{idPregunta}")
    public String formularioEditPregunta(@PathVariable("idPregunta") Long idPregunta, Model model) {
        model.addAttribute("preguntass", preguntaService.findById(idPregunta));
        model.addAttribute("tipoPregunta", tipoPreguntaService.findAll());
        model.addAttribute("tipoTest", tipoTestService.findAll());
        model.addAttribute("edit", "true");
        return "pregunta/formulario_pregunta";
    }

    @PostMapping("/registrarPregunta")
    public ResponseEntity<String> RegistrarPregunta(HttpServletRequest request,
                        @RequestParam("pregunta") String textoPregunta,
                        @RequestParam("tipoPregunta") Long id_tipo_pregunta,
                        @RequestParam("tipoTest") Long id_tipo_test) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            Pregunta preguntaExistente = preguntaService.findByPregunta(textoPregunta);

            if (preguntaExistente != null) {
                return ResponseEntity.ok("Ya existe este registro");
            }

            TipoPregunta tipoPregunta = tipoPreguntaService.findById(id_tipo_pregunta);
            TipoTest tipoTes = tipoTestService.findById(id_tipo_test); 

            Pregunta nuevaPregunta = new Pregunta();
            nuevaPregunta.setEstado("ACTIVO");
            nuevaPregunta.setPregunta(textoPregunta);
            nuevaPregunta.setTipoPregunta(tipoPregunta);
            nuevaPregunta.setTipoTest(tipoTes);
            nuevaPregunta.setRegistroIdUsuario(usuario.getIdUsuario());
            preguntaService.save(nuevaPregunta);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    }

    @PostMapping("/editarPregunta")
    public ResponseEntity<String> editarPregunta(HttpServletRequest request,
                            @RequestParam("idPregunta") Long idPregunta,
                            @RequestParam("pregunta") String textoPregunta,
                            @RequestParam("tipoPregunta") Long id_tipo_pregunta,
                            @RequestParam("tipoTest") Long id_tipo_test) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            Pregunta preguntaExistente = preguntaService.findById(idPregunta);

            if (preguntaExistente != null) {
                preguntaExistente.setPregunta(textoPregunta);
                preguntaExistente.setTipoPregunta(tipoPreguntaService.findById(id_tipo_pregunta));
                preguntaExistente.setTipoTest(tipoTestService.findById(id_tipo_test));
                preguntaExistente.setModificacionIdUsuario(usuario.getIdUsuario());
                preguntaService.save(preguntaExistente);

                return ResponseEntity.ok("Se modificó el registro con éxito");
            } else {
                return ResponseEntity.ok("No se encontró la pregunta para actualizar");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    }
}
