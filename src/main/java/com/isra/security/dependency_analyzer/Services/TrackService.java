package com.isra.security.dependency_analyzer.Services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> uploadSbomFromFile(String projectUuid, String filePath){
        String apiUrl = System.getenv("DEPENDENCY_TRACK_API_URL") + "/api/v1/bom";
        String apiKey = System.getenv("DEPENDENCY_TRACK_API_KEY");

        //create http header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", apiKey);

        //encode file content in Base64
        String encodedBom = encodeFileToBase64(filePath);
        //create a request body
        Map<String, Object> requestBody = Map.of(
                "project", projectUuid,
                //call readFile function
                "bom", encodedBom,
                "autoCreate", true
        );

        //create HTTP request entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        //send PUT request to Dependency-Track API
        return restTemplate.exchange(apiUrl, HttpMethod.PUT, requestEntity, String.class);
    }

    public List<Map<String, Object>> getVulnerabilities(String projectUuid) {
        String apiUrl = System.getenv("DEPENDENCY_TRACK_API_URL") + "/api/v1/finding/project/" + projectUuid;
        String apiKey = System.getenv("DEPENDENCY_TRACK_API_KEY");

        //create http header
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        //create HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //send GET request to Dependency-Track API
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        //return the list of vulnerabilities
        return response.getBody();
    }

    private String encodeFileToBase64(String filePath) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException("Error reading or encoding file: " + filePath, e);
        }
    }
}
