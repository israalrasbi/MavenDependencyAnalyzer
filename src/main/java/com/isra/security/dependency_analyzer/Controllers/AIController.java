package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping(value = "ai")
public class AIController {
    @Autowired
    private AIService aiService;

    @GetMapping("/analyze")
    public String analyzeVulnerabilities() throws IOException {
        // Read the vulnerabilities file
        File vulnerabilityFile = new File("output/vulnerabilities.json");

        if (!vulnerabilityFile.exists()) {
            return "Vulnerabilities file not found.";
        }

        // Read the vulnerabilities file content
        String vulnerabilitiesJson = new String(Files.readAllBytes(vulnerabilityFile.toPath()));

        // Parse the vulnerabilities into a list (you might want to use a library like Jackson for better parsing)
        List<String> vulnerabilities = parseVulnerabilities(vulnerabilitiesJson);

        // Process the vulnerabilities and get suggestions
        StringBuilder result = new StringBuilder();

        for (String vulnerability : vulnerabilities) {
            String fixSuggestions = aiService.generateFixSuggestions(vulnerability);
            result.append("Vulnerability: ").append(vulnerability)
                    .append("\nFix Suggestions: ").append(fixSuggestions).append("\n\n");
        }

        return result.toString();
    }

    // Simple method to parse the vulnerabilities from the JSON file content (you can improve this with a JSON parser)
    private List<String> parseVulnerabilities(String vulnerabilitiesJson) {
        // For now, let's assume the JSON is just a simple list of strings (you can replace this with a full JSON parser like Jackson)
        // Example: ["Critical vulnerability in X", "Minor issue in Y"]
        return List.of(vulnerabilitiesJson.split("\n")); // This is a placeholder; implement proper parsing
    }
}
