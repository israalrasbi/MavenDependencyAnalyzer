package com.isra.security.dependency_analyzer.Services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AIService {
    @Autowired
    private ChatClient chatClient;

    private static final String FILE_PATH = "output/vulnerabilities.json";

    public String analyzeVulnerabilities() {
        try {
            // Read file content
            String vulnerabilities = new String(Files.readAllBytes(Paths.get(FILE_PATH)));

            // Create prompt
            String prompt = "Analyze the following vulnerabilities and suggest how to fix them:\n" + vulnerabilities;

            // Call Spring AI ChatClient
            return chatClient.prompt()
                    .user(prompt)
                    .call()  // Get CallResponseSpec
                    .chatResponse()  // Get ChatResponse
                    .getResults().get(0).getOutput().getContent(); // Extract response text
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading the vulnerability file.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing AI response.";
        }
    }
}
