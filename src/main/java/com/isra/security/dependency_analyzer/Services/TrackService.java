package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.VulnerabilityResponse;
import com.isra.security.dependency_analyzer.Interfaces.DependencyTrackClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    private RestTemplate restTemplate;

    public void uploadSbom(String bomJson) {
        String apiUrl = System.getenv("DEPENDENCY_TRACK_API_URL") + "/bom";
        String apiKey = System.getenv("DEPENDENCY_TRACK_API_KEY");

        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", apiKey);

        // Create body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("project", "your_project_uuid_here"); // Replace with actual project UUID
        requestBody.put("bom", bomJson);
        requestBody.put("autoCreate", true);

        // Create HTTP request
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.PUT, requestEntity, String.class);

        // Print response
        System.out.println("Response: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());
    }
}
