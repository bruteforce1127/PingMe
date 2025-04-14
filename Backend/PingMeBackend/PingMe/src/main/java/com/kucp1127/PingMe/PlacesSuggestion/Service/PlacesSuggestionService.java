package com.kucp1127.PingMe.PlacesSuggestion.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PlacesSuggestionService {
    private final WebClient webClient;

    @Value("${adarsh.api.key}")
    private String apiKey;

    public PlacesSuggestionService(WebClient.Builder webClientBuilder) {
        // Base URL for the Gemini API
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    public Mono<String> generateChat(String username, String prompt) {

        String updatedPrompt = prompt +
                " Based on the provided location, please generate specific recommendations. " +
                "For instance, when suggesting places, format your suggestions with actual " +
                "names like 'you can go to [restaurant_name] restaurant' or 'visit [shop_name] shop'. " +
                "Include suggestions for restaurants, shopping places, and other noteworthy locations in a similar naming style.";


        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", updatedPrompt);

        Map<String, Object> partsWrapper = new HashMap<>();
        partsWrapper.put("parts", Collections.singletonList(textPart));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Collections.singletonList(partsWrapper));

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-2.0-flash:generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class);
    }

}
