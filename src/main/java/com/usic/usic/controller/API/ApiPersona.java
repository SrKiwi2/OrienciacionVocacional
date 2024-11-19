package com.usic.usic.controller.API;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usic.usic.model.DTO.PersonaDTO;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IPersonaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class ApiPersona {
    private final IPersonaService personaService;
    private final IEstudianteService estudianteService;

    private static final String API_KEY = "7fd4232f2344fw5423234214usic";

    //API PARA MANDAR DATOS DE ESTUDINATES ATRAVEZ DEL CI
    @PostMapping("/buscarPorCi")
    public ResponseEntity<?> obtenerPersonaPorCi(@RequestHeader(value = "API-Key", required = true) String apiKey,@RequestBody Map<String, Object> payload) {
        
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String ci = payload.get("ci").toString();

        if (personaService.validarCI(ci) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("El CI proporcionado no corresponde a ningún estudiante registrado.");
        }
        
        Persona persona_encontrada = personaService.validarCI(ci);
        Estudiante estudiante = estudianteService.findByPersona(persona_encontrada);

        String test_realizado = estudianteService.hasCompletedChasideTest(estudiante.getIdEstudiante());
        System.out.println(estudiante.getIdEstudiante());
        System.out.println(test_realizado);

        PersonaDTO personaDTO = personaService.obtenerPersonaPorCi(ci);
        personaDTO.setUrl_certificado("http://virtual.uap.edu.bo:9597/reporte/pdf/"+estudiante.getIdEstudiante());
        personaDTO.setTestRealizado(test_realizado);

        return ResponseEntity.ok(personaDTO);
    }
}
