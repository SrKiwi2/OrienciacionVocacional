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
import com.usic.usic.model.Service.IPersonaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class ApiPersona {
    private final IPersonaService personaService;

    private static final String API_KEY = "7fd4232f2344fw5423234214usic";

    //API PARA MANDAR DATOS DE ESTUDINATES ATRAVEZ DEL CI
    @PostMapping("/buscarPorCi")
    public ResponseEntity<PersonaDTO> obtenerPersonaPorCi(@RequestHeader(value = "API-Key", required = true) String apiKey,@RequestBody Map<String, Object> payload) {

        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String ci = payload.get("ci").toString();
        PersonaDTO personaDTO = personaService.obtenerPersonaPorCi(ci);
        return ResponseEntity.ok(personaDTO);
    }
}
