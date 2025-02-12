package com.isra.security.dependency_analyzer.Services;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AIService {
    @Value("${huggingface.api.token}")
    private String huggingFaceApiToken;

    private static final String API_URL = "https://api-inference.huggingface.co/models/gpt2";  // Change to the model of your choice

    public String generateFixSuggestions(String vulnerabilityDescription) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + huggingFaceApiToken);

        String prompt = "Suggest possible fixes for the following security vulnerability: " + vulnerabilityDescription;

        // Prepare the request payload
        String body = "{ \"inputs\": \"" + prompt + "\" }";
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        // Send the request
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        // Process and return the generated response
        return response.getBody(); // Handle the result as needed (parsing response, etc.)
    }

}
