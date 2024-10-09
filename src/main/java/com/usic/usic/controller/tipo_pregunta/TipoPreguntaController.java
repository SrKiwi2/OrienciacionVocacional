package com.usic.usic.controller.tipo_pregunta;

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

import com.usic.usic.model.Entity.TipoPregunta;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.ITipoPreguntaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TipoPreguntaController {

    @Autowired
    private ITipoPreguntaService tipoPreguntaService;

    @GetMapping(value = "/adm_vista_tipo_pregunta")
    public String administracionTipoPregunta(Model model) {
        return "tipoPregunta/vista_tipo_pregunta";
    }
    
    @PostMapping("/listarTipoPreguntas")
    public String listarTipoPreguntas(HttpServletRequest request, Model model) {
        model.addAttribute("tipoPreguntass", tipoPreguntaService.findAll());
        return "tipoPregunta/tabla_tipo_pregunta";
    }

    @PostMapping("/FormularioTipoPregunta")
    public String FormularioTipoPregunta(HttpServletRequest request, Model model) {
        model.addAttribute("tipoPreguntass", new TipoPregunta());
        return "tipoPregunta/formulario_tipo_pregunta";
    }

    @GetMapping("/formularioEditTipoPregunta/{id_tipo_pregunta}")
    public String formularioEditTipoPregunta(@PathVariable("id_tipo_pregunta") Long id_tipo_pregunta, Model model) {
        model.addAttribute("tipoPreguntass", tipoPreguntaService.findById(id_tipo_pregunta));
        model.addAttribute("edit", "true");
        return "tipoPregunta/formulario_tipo_pregunta";
    }

    @PostMapping("/registrarTipoPregunta")
    public ResponseEntity<String> RegistrarTipoPregunta(HttpServletRequest request, @Validated TipoPregunta tipoPregunta) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {

            TipoPregunta tipoPreguntaExistente = tipoPreguntaService.findByTipoPregunta(tipoPregunta.getTipoPregunta());

            if (tipoPreguntaExistente != null) {
                return ResponseEntity.ok("Ya existe este registro");
            }

            TipoPregunta nuevoTipoPregunta = new TipoPregunta();
            nuevoTipoPregunta.setTipoPregunta(tipoPregunta.getTipoPregunta());
            nuevoTipoPregunta.setDescripcion(tipoPregunta.getDescripcion());
            nuevoTipoPregunta.setEstado("ACTIVO");
            nuevoTipoPregunta.setRegistroIdUsuario(usuario.getIdUsuario());
            tipoPreguntaService.save(nuevoTipoPregunta);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    }

    @PostMapping("/editarTipoPregunta")
    public ResponseEntity<String> editarTipoPregunta(HttpServletRequest request,
                            @RequestParam("idTipoPregunta") Long id_tipo_pregunta,
                            @RequestParam("tipoPregunta") String tipoPregunta,
                            @RequestParam("descripción") String descripcion) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            TipoPregunta tipoPreguntaExistente = tipoPreguntaService.findById(id_tipo_pregunta);

            if (tipoPreguntaExistente != null) {
                tipoPreguntaExistente.setTipoPregunta(tipoPregunta);
                tipoPreguntaExistente.setDescripcion(descripcion);
                tipoPreguntaExistente.setEstado("ACTIVO");
                tipoPreguntaExistente.setRegistroIdUsuario(usuario.getIdUsuario());
                tipoPreguntaService.save(tipoPreguntaExistente);

                return ResponseEntity.ok("Se modificó el registro con éxito");
            } else {
                return ResponseEntity.ok("No se encontró la pregunta para actualizar");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    }
}
