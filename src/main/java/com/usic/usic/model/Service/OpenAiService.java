package com.usic.usic.model.Service;

import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class OpenAiService {

    private final WebClient webClient;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public OpenAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(OPENAI_API_URL).build();
    }

    public String getOpinion(String prompt) {
        // Crear el objeto para la petición
        OpenAiChatRequest request = new OpenAiChatRequest(prompt);

        int attempts = 0;
        int maxAttempts = 5; // Limita el número de intentos
        long waitTime = 1000; // Tiempo de espera inicial en milisegundos

        // Hacer la solicitud a la API de OpenAI
        while (attempts < maxAttempts) {
            try {
                Mono<OpenAiResponse> responseMono = this.webClient.post()
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .body(Mono.just(request), OpenAiChatRequest.class)
                        .retrieve()
                        .bodyToMono(OpenAiResponse.class);

                OpenAiResponse response = responseMono.block();
                if (response != null && !response.getChoices().isEmpty()) {
                    return response.getChoices().get(0).getText().trim();
                }
                return "No se pudo obtener una respuesta de la IA.";
            } catch (WebClientResponseException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    attempts++;
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                    waitTime *= 2; // Duplicar el tiempo de espera
                } else {
                    throw e; // Lanza la excepción si es otro error
                }
            }
        }

        return "Se ha superado el límite de intentos.";
    }

    static class OpenAiChatRequest {
        private String model;
        private List<Map<String, String>> messages;
        private int max_tokens;
        private double temperature;

        public OpenAiChatRequest(String prompt) {
            this.model = "gpt-3.5-turbo"; // El modelo a usar (puedes cambiarlo si es necesario)
            this.messages = List.of(
                Map.of("role", "system", "content", "Eres un asistente de orientación vocacional."),
                Map.of("role", "user", "content", prompt)
            );
            this.max_tokens = 200000;           // Máximo número de tokens de la respuesta
            this.temperature = 0.7;          // Grado de aleatoriedad de la respuesta
        }

        // Getters y setters
        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getMaxTokens() {
            return max_tokens;
        }

        public void setMaxTokens(int max_tokens) {
            this.max_tokens = max_tokens;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }

    static class OpenAiResponse {
        private List<Choice> choices;

        // Getters y setters
        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

        static class Choice {
            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
