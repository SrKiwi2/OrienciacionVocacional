package com.usic.usic.controller.Test;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Respuesta;
import com.usic.usic.model.Entity.RespuestaDTO;
import com.usic.usic.model.Entity.Test;
import com.usic.usic.model.Service.IRespuestaService;
import com.usic.usic.model.Service.ITestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
public class TestCentralController {
    @Autowired
    private ITestService testService;

    @Autowired
    private IRespuestaService respuestaService;

    @GetMapping("/vista_habilidades_sociales")
    public String vistaTestHabilidadesSociales() {
        return "/test/test_habilidades_sociales/habilidades_sociales.html";
    }

    @GetMapping("/{nombreTest}/preguntas")
    public String obtenerPreguntasDelTest(@PathVariable String nombreTest, Model model) {
        Test test = testService.obtenerTestPorNombre(nombreTest);
        List<Pregunta> preguntas = testService.obtenerPreguntaDelTest(test);

        // Verificar si se están obteniendo las preguntas
        System.out.println("Preguntas: " + preguntas.size());

        model.addAttribute("preguntas", preguntas);
        model.addAttribute("respuestas", new ArrayList<>()); // Prepara la lista de respuestas vacía
        return "/test/test_habilidades_sociales/habilidades_sociales"; // Asegúrate de que este sea el nombre de tu archivo HTML
    }

    @PostMapping("/{nombreTest}/responder")
    public ResponseEntity<String> guardarRespuestas(@RequestBody List<RespuestaDTO> respuestas) {
        for (RespuestaDTO respuestaDTO : respuestas) {
            // Crear la respuesta y guardarla en la BD
            Respuesta respuesta = new Respuesta();
            respuesta.setPregunta(new Pregunta(respuestaDTO.getIdPregunta()));
            // respuesta.setEstudiante(new Estudiante(respuestaDTO.getIdEstudiante()));
            respuesta.setRespuesta(respuestaDTO.getRespuesta());
            respuestaService.guardarRespuesta(respuesta);
        }
        return ResponseEntity.ok("Respuestas guardadas con éxito");
    }
}
