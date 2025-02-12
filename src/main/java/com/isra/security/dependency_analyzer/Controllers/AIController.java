package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "ai")
public class AIController {
    @Autowired
    private AIService aiService;

   /* @GetMapping("/analyze")
    public String analyzeVulnerabilities() {
        return aiService.generateFixSuggestions();
    }*/

    // Simple method to parse the vulnerabilities from the JSON file content (you can improve this with a JSON parser)
    private List<String> parseVulnerabilities(String vulnerabilitiesJson) {
        // For now, let's assume the JSON is just a simple list of strings (you can replace this with a full JSON parser like Jackson)
        // Example: ["Critical vulnerability in X", "Minor issue in Y"]
        return List.of(vulnerabilitiesJson.split("\n")); // This is a placeholder; implement proper parsing
    }
}
