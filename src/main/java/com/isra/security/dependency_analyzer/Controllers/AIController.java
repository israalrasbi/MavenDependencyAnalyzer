package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "ai")
@CrossOrigin("*")
public class AIController {
    @Autowired
    private AIService aiService;

    @PostMapping("/analyzeVulnerabilities")
    public ResponseEntity<String> analyzeVulnerabilities(@RequestParam("filePath") String filePath) {
        try {
            String vulnerabilitiesJson = aiService.readVulnerabilitiesFile(filePath);
            String aiResponse = aiService.analyzeVulnerabilities(vulnerabilitiesJson);
            return ResponseEntity.ok(aiResponse);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
