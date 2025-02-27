package com.isra.security.dependency_analyzer.Services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    @Value("${gemini.api.key}")
    private String GEMINI_API_KEY;

    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    private final RestTemplate restTemplate;

    public AIService() {
        this.restTemplate = new RestTemplate();
    }

    public String analyzeVulnerabilities(String jsonContent) {
        //construct the full url
        String fullUrl = API_URL + "?key=" + GEMINI_API_KEY;

        //prepare request body
        Map<String, Object> requestBody = Map.of(
                "contents", Collections.singletonList(
                        Map.of("parts", Collections.singletonList(
                                Map.of("text", "Analyze these vulnerabilities and suggest fixes in 4 bullet points:\n" + jsonContent)
                        ))
                )
        );

        //set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        //send post request
        ResponseEntity<Map> response = restTemplate.exchange(fullUrl, HttpMethod.POST, request, Map.class);

        //extract the actual text response from JSON structure
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return extractText(response.getBody());
        } else {
            return "Error: Unable to analyze vulnerabilities.";
        }
    }

    public String readVulnerabilitiesFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    //extract text from gemini response
    private String extractText(Map<String, Object> responseBody) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return "Error: No response from AI.";
            }

            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");

            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            if (parts == null || parts.isEmpty()) {
                return "Error: No text content.";
            }

            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "Error parsing AI response: " + e.getMessage();
        }
    }

}
