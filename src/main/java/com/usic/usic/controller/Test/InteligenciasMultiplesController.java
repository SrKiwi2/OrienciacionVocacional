package com.usic.usic.controller.Test;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.EstudianteRespuesta;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Repository.Sp_preguntas;
import com.usic.usic.model.Service.IEstudianteRespuestaService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IPreguntaService;
import com.usic.usic.model.Service.IRespuestaService;
import com.usic.usic.model.Service.IResultadoIaService;
import com.usic.usic.model.Service.ITipoTestService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class InteligenciasMultiplesController {
    
    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IPreguntaService preguntaService;

    @Autowired
    private IRespuestaService respuestaService;

    @Autowired
    private Sp_preguntas sp_preguntas;

    @Autowired
    private ITipoTestService tipoTestService;

    @Autowired
    private IResultadoIaService resultadoIaService;

    @Autowired
    private IEstudianteRespuestaService estudianteRespuestaService;

    @Value("${spring.ai.openai.chat.api-key}")
    private String apiKey;

    @GetMapping("/inteligencias_multiples/{idTipoTest}")
    public String inteligencias_multiples(@PathVariable Long idTipoTest, Model model, HttpSession session) {

        Boolean testHsFinalizado = (Boolean) session.getAttribute("testIMFinalizado");
        if (testHsFinalizado != null && testHsFinalizado) {
            return "test/resultado_tests/resultados_tests";
        }else{
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());
            Long idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);
            Long contadorPreguntas = tipoTestService.countDistinctPreguntasNotRespondidas(idTipoTest, estudiante.getIdEstudiante());
            System.out.println("inteligencias_multiples");
            System.out.println(contadorPreguntas);
               
            model.addAttribute("mostrarCargando", contadorPreguntas == 0);
            model.addAttribute("v_idTipoTest", idTipoTest);
            model.addAttribute("respuestasRespondidas", sp_preguntas.ObtenerRespuestasrespondidas(estudiante.getIdEstudiante(), idTipoTest));
    
            if (idPregunta != 0 && contadorPreguntas != 0) {
    
                Pregunta pregunta = preguntaService.findById(idPregunta);
                
                model.addAttribute("pregunta", pregunta);
                model.addAttribute("respuestas",  respuestaService.findAll());
                model.addAttribute("registro_pre_test", new EstudianteRespuesta());
                return "test/vista_pregunta";
            } else {
    
                model.addAttribute("pregunta", "No hay preguntas disponibles.");
                return "redirect:/interpretar_respuestas_im";
            }
        }
    }

    @GetMapping("/interpretar_respuestas_im")
    public String interpretarRespuestasIm(Model model, HttpSession session, ResultadoIA resultadoIA) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());

        List<Object[]> preguntasYRespuestas = estudianteRespuestaService.findPreguntasYRespuestasConSI(estudiante.getIdEstudiante());

        StringBuilder promptIntereses = new StringBuilder("El estudiante ha respondido 'SI' a las siguientes preguntas:\n");
        for (Object[] pr : preguntasYRespuestas) {
            String pregunta = (String) pr[0];
            promptIntereses.append("- ").append(pregunta).append("\n");
        }

        promptIntereses.append("\nPor favor, analiza estas preguntas y respuestas desde la perspectiva de un evaluador psicopedagogo.");
        promptIntereses.append("Estas son preguntas que evaluan mis inteligencias.");
        promptIntereses.append("Utiliza un tono positivo e identifica y describe mis 3 inteligencias principales\n");
        promptIntereses.append("Sé encantador y utiliza frases como 'tus principales habilidades son...'. que sea breve y conciso, maximo de 50 palabras.");

        String InteligenciasMultiples = llamarAI(promptIntereses.toString());

        TipoTest tipoTest = tipoTestService.findById(2L);

        resultadoIA.setEstudiante(estudiante);
        resultadoIA.setResultado(InteligenciasMultiples);
        resultadoIA.setTipoTest(tipoTest);
        resultadoIA.setEstado("ACTIVO");
        resultadoIaService.save(resultadoIA);

        model.addAttribute("mostrarModal", true);
        return "redirect:/finTestInteligenciasMultiples";
    }

    @GetMapping("/finTestInteligenciasMultiples")
    public String vista_resultado_im(Model model, HttpSession session, HttpServletResponse response) {

        session.setAttribute("testIMFinalizado", true);

        return "test/resultado_tests/resultados_tests";
    }

    private String llamarAI(String prompt) {

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o-mini");

        JSONArray messages = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "Eres un psicopedagogo que analiza las preguntas y respuestas de estudiantes para saber mis inteligencias, como: Inteligencia lisnguistica, logico - matematica, espacial, musical, etc.");
        messages.put(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.put(userMessage);

        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 1000);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        for (int i = 0; i < 3; i++) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
                JSONObject jsonResponse = new JSONObject(response.getBody());
                return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        }
        return "No se pudo obtener la interpretación de la IA.";
    }
}
