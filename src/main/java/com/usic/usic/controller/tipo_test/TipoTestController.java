package com.usic.usic.controller.tipo_test;

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

import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TipoTestController {
    
    @Autowired
    private ITipoTestService tipoTestService;
    
    @GetMapping(value = "/adm_vista_tipo_test")
    public String administracionTipoTest(Model model) {
        return "tipoTests/vista_tipo_tests";
    }
    
    @PostMapping("/listarTipoTests")
    public String listarTipoTests(HttpServletRequest request, Model model) {
        model.addAttribute("tipoTestss", tipoTestService.findAll());
        return "tipoTests/tabla_tipo_tests";
    }

    @PostMapping("/FormularioTipoTests")
    public String FormularioTipoTests(HttpServletRequest request, Model model) {
        model.addAttribute("tipoTestss", new TipoTest());
        return "tipoTests/formulario_tipo_test";
    }

    @GetMapping("/formularioEditTipoTests/{id_tipo_test}")
    public String formularioEditTipoTests(@PathVariable("id_tipo_test") Long id_tipo_test, Model model) {
        model.addAttribute("tipoTestss", tipoTestService.findById(id_tipo_test));
        model.addAttribute("edit", "true");
        return "tipoTests/formulario_tipo_test";
    }

    @PostMapping("/registrarTipoTest")
    public ResponseEntity<String> registrarTipoTest(HttpServletRequest request, @Validated TipoTest tipoTest) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {

            TipoTest tipoTestExistente = tipoTestService.findByTipoTest(tipoTest.getTipoTest());

            if (tipoTestExistente != null) {
                return ResponseEntity.ok("Ya existe este registro");
            }

            TipoTest nuevoTipoTest = new TipoTest();
            nuevoTipoTest.setTipoTest(tipoTest.getTipoTest());
            nuevoTipoTest.setDescripcion(tipoTest.getDescripcion());
            nuevoTipoTest.setEstado("ACTIVO");
            nuevoTipoTest.setRegistroIdUsuario(usuario.getIdUsuario());
            tipoTestService.save(nuevoTipoTest);
            return ResponseEntity.ok("Se guardó el registro con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    }

    @PostMapping("/editarTipoTest")
    public ResponseEntity<String> editarTipoTest(HttpServletRequest request,
                            @RequestParam("idTipoTest") Long idTipoTest,
                            @RequestParam("tipoTest") String tipoTest,
                            @RequestParam("descripción") String descripcion) {

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            TipoTest tipoTestExistente = tipoTestService.findById(idTipoTest);

            if (tipoTestExistente != null) {
                tipoTestExistente.setTipoTest(tipoTest);
                tipoTestExistente.setDescripcion(descripcion);
                tipoTestExistente.setEstado("ACTIVO");
                tipoTestExistente.setRegistroIdUsuario(usuario.getIdUsuario());
                tipoTestService.save(tipoTestExistente);

                return ResponseEntity.ok("Se modificó el registro con éxito");
            } else {
                return ResponseEntity.ok("No se encontró la pregunta para actualizar");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    }
}
