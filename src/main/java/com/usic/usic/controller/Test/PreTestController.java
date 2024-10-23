package com.usic.usic.controller.Test;

import java.util.Date;
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

import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.EstudianteRespuesta;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Pregunta;
import com.usic.usic.model.Entity.Respuesta;
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
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class PreTestController {

    @Autowired
    private IPreguntaService preguntaService;

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IRespuestaService respuestaService;

    @Autowired
    private IEstudianteRespuestaService estudianteRespuestaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private Sp_preguntas sp_preguntas;

    @Autowired
    private IResultadoIaService resultadoIaService;

    @Autowired
    private ITipoTestService tipoTestService;

    @Value("${spring.ai.openai.chat.api-key}")
    private String apiKey;

    @PostMapping("/requisitos_estudiantes")
    public String requisitosEstudiante(HttpServletRequest request, Model model) {
        return "test/pre-test/requisitos";
    }

    @GetMapping("/pre_test/{idTipoTest}")
    public String pre_test(@PathVariable Long idTipoTest, Model model, HttpSession session) {

        Boolean testFinalizado = (Boolean) session.getAttribute("testFinalizado");
        if (testFinalizado != null && testFinalizado) {
            return "redirect:/vista_resultado_pre_test_ia";
        }else{
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());
            Long idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);
            Long contadorPreguntas = tipoTestService.countDistinctPreguntasNotRespondidas(idTipoTest, estudiante.getIdEstudiante());

            System.out.println("Estoy aqui mi señor u.u 1");

            model.addAttribute("mostrarCargando", contadorPreguntas == 1);
            model.addAttribute("v_idTipoTest", idTipoTest);
            model.addAttribute("respuestasRespondidas", sp_preguntas.ObtenerRespuestasrespondidas(estudiante.getIdEstudiante(), idTipoTest));
            
            if (idPregunta != 0) {
                Pregunta pregunta = preguntaService.findById(idPregunta);
                model.addAttribute("pregunta", pregunta);
                model.addAttribute("respuestas",  respuestaService.findAll());
                model.addAttribute("registro_pre_test", new EstudianteRespuesta());
                return "test/vista_pregunta";
            } else {
                model.addAttribute("pregunta", "No hay preguntas disponibles.");
                return "redirect:/interpretar_respuestas";
            }
        }
    }

    @GetMapping("/interpretar_respuestas")
    public String interpretarRespuestas(Model model, HttpSession session, ResultadoIA resultadoIA) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());

        List<Object[]> preguntasYRespuestas = estudianteRespuestaService.findPreguntasYRespuestasConSI(estudiante.getIdEstudiante());

        StringBuilder promptIntereses = new StringBuilder("El estudiante ha respondido 'SI' a las siguientes preguntas:\n");
        for (Object[] pr : preguntasYRespuestas) {
            String pregunta = (String) pr[0];
            promptIntereses.append("- ").append(pregunta).append("\n");
        }

        promptIntereses.append("\nPor favor, analiza estas preguntas y respuestas desde la perspectiva de un evaluador psicopedagogo.");
        promptIntereses.append("Proporcioname una respuesta motivadora, clara y específica, enfocándote en mis intereses segun tu opinion.");
        promptIntereses.append("Utiliza un tono positivo y menciona cuáles son mis intereses, resaltando mis fortalezas. \n");
        promptIntereses.append("Sé encantador y utiliza frases como 'tus intereses son...'. que sea breve y conciso, maximo de 50 palabras.");

        String interpretacionIntereses = llamarAI(promptIntereses.toString());

        StringBuilder promptAptitudes = new StringBuilder("El estudiante ha respondido 'SI' a las siguientes preguntas:\n");
        for (Object[] pr : preguntasYRespuestas) {
            String pregunta = (String) pr[0];
            promptAptitudes.append("- ").append(pregunta).append("\n");
        }

        promptAptitudes.append("\nPor favor, analiza estas preguntas y respuestas desde la perspectiva de un evaluador psicopedagogo.");
        promptAptitudes.append("Proporciona una respuesta motivadora, clara y específica, enfocándote en mis aptitudes segun tu opinion.");
        promptAptitudes.append("Explica mis aptitudes de forma alentadora, y describe mis fortalezas con detalles específicos. \n");
        promptAptitudes.append("Sé encantador y utiliza frases como 'tus aptitudes son...'. que sea breve y conciso. maximo de 50 palabras.");

        String interpretacionAptitudes = llamarAI(promptAptitudes.toString());

        StringBuilder promptAreas = new StringBuilder("Basándote en los siguientes intereses y aptitudes que me haz proporcionado:\n");
        promptAreas.append("Intereses: ").append(interpretacionIntereses).append("\n");
        promptAreas.append("Aptitudes: ").append(interpretacionAptitudes).append("\n");
        promptAreas.append("Determina las áreas profesionales más adecuadas para mi. \n");
        promptAreas.append("Ejemplos de áreas son: Área Administrativa, Área de Humanidades y Ciencias Sociales y Jurídicas, Área Artística, Área de Ciencias de la Salud, etc. \n");
        promptAreas.append("Sé específico y proporciona una lista clara de las áreas que podrían corresponder a los intereses y aptitudes que me has proporcionado.");
        promptAreas.append("Máximo de 50 palabras.");

        String interpretacionAreas = llamarAI(promptAreas.toString());

        session.setAttribute("opinionIAIntereses", interpretacionIntereses);
        session.setAttribute("opinionIAAptitudes", interpretacionAptitudes);
        session.setAttribute("opinionIAAreas", interpretacionAreas); 
        session.setAttribute("idEstudiante", estudiante.getIdEstudiante());

        String respuestaIaEstudiante = interpretacionIntereses + "/" + interpretacionAptitudes + "/" + interpretacionAreas;

        TipoTest tipoTest = tipoTestService.findById(1L);

        resultadoIA.setEstudiante(estudiante);
        resultadoIA.setResultado(respuestaIaEstudiante);
        resultadoIA.setTipoTest(tipoTest);
        resultadoIA.setEstado("ACTIVO");
        resultadoIaService.save(resultadoIA);
        return "redirect:/vista_resultado_pre_test_ia";
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
        systemMessage.put("content", "Eres un psicopedagogo que analiza las preguntas y respuestas de estudiantes para saber sus actitudes, interes y .");
        messages.put(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.put(userMessage);

        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 1000);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 3; i++) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
                long endTime = System.currentTimeMillis(); // Tiempo de finalización
                long duration = endTime - startTime;

                System.out.println("Tiempo de respuesta de la IA: " + duration + " ms");

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
    
    @PostMapping("/guardar_respuesta")
    public String guardar_respuesta(@RequestParam("respuesta_pregunta") Long respuesta_pregunta,@RequestParam("v_idTipoTest") Long id_tipo_test, HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Respuesta respuesta = respuestaService.findById(respuesta_pregunta);
        EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
        estudianteRespuesta.setEstado("ACTIVO");
        estudianteRespuesta.setComplemento("n/a");
        estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
        estudianteRespuesta.setRespuesta(respuesta);
        estudianteRespuestaService.save(estudianteRespuesta);

        String redirectUrl;

        switch (id_tipo_test.intValue()) {
            case 1:
                redirectUrl = "redirect:/pre_test/" + id_tipo_test;
                break;
            case 2:
                redirectUrl = "redirect:/habilidades_sociales/" + id_tipo_test;
                break;
            case 3:
                redirectUrl = "redirect:/inteligencias_multiples/" + id_tipo_test;
                break;
            case 4:
                redirectUrl = "redirect:/intereses_profesionales/" + id_tipo_test;
                break;
            default:
                redirectUrl = "redirect:/default";
                break;
        }

         return redirectUrl;
    }

    @PostMapping("/cargarPreguntas")
    public String cargarPreguntas(@RequestParam("idTipoTest") Long idTipoTest, HttpServletRequest request, Model model) {
        System.out.println("Me he ejecutado mi señor u.u");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Estudiante estudiante = estudianteService.findByPersona(usuario.getPersona());


        model.addAttribute("v_idTipoTest", idTipoTest);

        Long idPregunta = preguntaService.findMaxRespuestaOrMinPregunta(estudiante.getIdEstudiante(), idTipoTest);
        if (idPregunta != 0) {
            Pregunta pregunta = preguntaService.findById(idPregunta);
            model.addAttribute("pregunta", pregunta);
            model.addAttribute("respuestas", respuestaService.findAll());
            return "test/fragmento_pregunta";
        } else {
            model.addAttribute("pregunta", "No hay más preguntas disponibles.");
            return "test/fragmento_pregunta";
        }
    }

    @GetMapping("/vista_resultado_pre_test_ia")
    public String vista_resultado_pre_test(Model model, HttpSession session, HttpServletResponse response) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        Long idEstudiante = (Long) session.getAttribute("idEstudiante");
        String intereses = (String) session.getAttribute("opinionIAIntereses");
        String aptitudes = (String) session.getAttribute("opinionIAAptitudes");
        String areas = (String) session.getAttribute("opinionIAAreas");

        session.setAttribute("testFinalizado", true);

        model.addAttribute("opinionIAIntereses", intereses);
        model.addAttribute("opinionIAAptitudes", aptitudes);
        model.addAttribute("opinionIAAreas", areas);
        model.addAttribute("idEstudiante", idEstudiante);

        return "test/pre-test/vista_resultado_pre_test";
    }

    @PostMapping("/terminar_test")
    public String terminarTest(HttpSession session) {
        // Limpiar los atributos de sesión relacionados con el test
        session.removeAttribute("idEstudiante");
        session.removeAttribute("opinionIAIntereses");
        session.removeAttribute("opinionIAAptitudes");
        session.removeAttribute("opinionIAAreas");
        session.removeAttribute("testFinalizado");
        session.invalidate(); // Invalida toda la sesión si quieres eliminar todos los datos

        return "redirect:/";  // Redirigir al inicio de la aplicación
    }

    // FIN PRE TEST

    @PostMapping("/guardar_respuesta2")
    public String guardar_respuesta2(@RequestParam(value = "checkboxes", required = false) List<String> checkboxes,
                                    @RequestParam(value = "textInputs", required = false) List<String> textInputs, HttpServletRequest request) {
        Persona persona = (Persona) request.getSession().getAttribute("persona");
        
        if (checkboxes != null && textInputs != null) {

            for (int i = 0; i < checkboxes.size(); i++) {
                Long checkboxValue = Long.valueOf(checkboxes.get(i));
                String textValue = textInputs.get(i);

                Respuesta respuesta = respuestaService.findById(checkboxValue);

                EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
                estudianteRespuesta.setEstado("ACTIVO");
                estudianteRespuesta.setComplemento(textValue);
                estudianteRespuesta.setRespuesta(respuesta);
                estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
                estudianteRespuestaService.save(estudianteRespuesta);
            }
        }
        return "redirect:/pre_test";
    }
    
    @PostMapping("/guardar_respuesta3")
    public String guardar_respuesta3(@RequestParam("respuesta_pregunta") String respuesta_pregunta, @RequestParam("id_pregunta") Long id_pregunta, HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Usuario usuario = usuarioService.findByPersona(persona);
        Pregunta pregunta = preguntaService.findById(id_pregunta);

        Respuesta respuesta = new Respuesta();

        respuesta.setEstado("ACTIVO");
        respuesta.setModificacion(new Date());
        respuesta.setModificacionIdUsuario(usuario.getIdUsuario());
        respuesta.setRegistro(new Date());
        respuesta.setRegistroIdUsuario(usuario.getIdUsuario());
        respuesta.setRespuesta(respuesta_pregunta);
        respuesta.setPregunta(pregunta);
        respuestaService.save(respuesta);

        EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
        estudianteRespuesta.setEstado("ACTIVO");
        estudianteRespuesta.setComplemento("n/a");
        estudianteRespuesta.setRespuesta(respuesta);
        estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
        estudianteRespuesta.setModificacion(new Date());
        estudianteRespuesta.setModificacionIdUsuario(usuario.getIdUsuario());
        estudianteRespuesta.setRegistro(new Date());
        estudianteRespuesta.setRegistroIdUsuario(usuario.getIdUsuario());
        estudianteRespuestaService.save(estudianteRespuesta);

        return "redirect:/pre_test";
    }

    @PostMapping("/guardar_respuesta4")
    public String guardar_respuesta4(@RequestParam(value = "checkboxes2", required = false) List<String> checkboxes, @RequestParam(value = "textInputs", required = false) List<String> textInputs,HttpServletRequest request) {

        Persona persona = (Persona) request.getSession().getAttribute("persona");
        Usuario usuario = usuarioService.findByPersona(persona);

        if (checkboxes != null) {
            for (int i = 0; i < checkboxes.size(); i++) {
                Long checkboxValue = Long.valueOf(checkboxes.get(i));
                Respuesta respuesta = respuestaService.findById(checkboxValue);
                EstudianteRespuesta estudianteRespuesta = new EstudianteRespuesta();
                estudianteRespuesta.setEstado("ACTIVO");
                estudianteRespuesta.setComplemento("n/a");
                estudianteRespuesta.setRespuesta(respuesta);
                estudianteRespuesta.setEstudiante(estudianteService.findByPersona(persona));
                estudianteRespuesta.setModificacion(new Date());
                estudianteRespuesta.setModificacionIdUsuario(usuario.getIdUsuario());
                estudianteRespuesta.setRegistro(new Date());
                estudianteRespuesta.setRegistroIdUsuario(usuario.getIdUsuario());
                estudianteRespuestaService.save(estudianteRespuesta);
            }
        }
        return "redirect:/pre_test";
    }

    @GetMapping("/pre_test_modificar")
    public String pre_test_modificar(@RequestParam("id_pregunta_respuesta") Long id_pregunta_respuesta, Model model, HttpSession session) {

        EstudianteRespuesta estudianteRespuesta = estudianteRespuestaService.findById(id_pregunta_respuesta);
        Estudiante estudiante = estudianteService.findById(estudianteRespuesta.getEstudiante().getIdEstudiante());
        Respuesta respuesta = respuestaService.findById(estudianteRespuesta.getRespuesta().getIdRespuesta());
        Pregunta pregunta = preguntaService.findById(respuesta.getPregunta().getIdPregunta());

        TipoTest tipoTest = tipoTestService.findById(pregunta.getTipoTest().getId_tipo_test());

        model.addAttribute("v_id_pregunta_respuesta", id_pregunta_respuesta);
        model.addAttribute("v_idTipoTest", tipoTest.getId_tipo_test());
        model.addAttribute("respuestasRespondidas", sp_preguntas.ObtenerRespuestasrespondidas(estudiante.getIdEstudiante(), tipoTest.getId_tipo_test()));
        model.addAttribute("estudianteRespuesta", estudianteRespuesta);
        model.addAttribute("respuesta", respuesta);
        model.addAttribute("respuestas",  respuestaService.findAll());
        model.addAttribute("pregunta", pregunta);

        return "test/vista_pregunta_modificar";
    }

    @PostMapping("/guardar_respuesta_modificacion")
    public String guardar_respuesta_modificacion(
        @RequestParam("id_estudianteRespuesta") Long id_estudianteRespuesta,
        @RequestParam("respuesta_pregunta") Long respuesta_pregunta,
        @RequestParam("v_idTipoTest") Long id_tipo_test, HttpServletRequest request) {

        EstudianteRespuesta estudianteRespuesta = estudianteRespuestaService.findById(id_estudianteRespuesta);
        Respuesta respuesta = respuestaService.findById(respuesta_pregunta);
        
        estudianteRespuesta.setRespuesta(respuesta);
        estudianteRespuesta.setModificacion(new Date());
        estudianteRespuestaService.save(estudianteRespuesta);
        return "redirect:/pre_test/"+id_tipo_test;
    }


    @GetMapping("/pre_test_prueba")
    public String pre_test_prueba(Model model, HttpSession session) {
        
        return "test/pruebas/vista_test_prueba";
    } 

    @GetMapping("/vista_pregunta")
    public String vista_pregunta(Model model, HttpSession session) {

        
        return "test/pruebas/vista_pregunta";
    }
}
