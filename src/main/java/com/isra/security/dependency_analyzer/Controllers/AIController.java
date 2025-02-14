package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "ai")
public class AIController {
    @Autowired
    private AIService aiService;

    @PostMapping("/analyzeVulnerabilities")
    public ResponseEntity<String> analyzeVulnerabilities(@RequestParam("filePath") String filePath) {
        try {
            // Ensure the path points to vulnerabilities.json
            String vulnerabilitiesJson = aiService.readVulnerabilitiesFile(filePath);

            // Call AI to analyze vulnerabilities
            String aiResponse = aiService.analyzeVulnerabilities(vulnerabilitiesJson);

            return ResponseEntity.ok(aiResponse);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
