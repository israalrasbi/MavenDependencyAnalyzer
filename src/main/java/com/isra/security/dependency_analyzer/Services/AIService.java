package com.isra.security.dependency_analyzer.Services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
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

    @Autowired
    public AIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analyzeVulnerabilities() {
        try {
            String vulnerabilities = Files.readString(Paths.get(FILE_PATH));

            // Create the Prompt for the AI
            Prompt prompt = new Prompt(vulnerabilities);

            // Call the model and get the response
            return chatClient.chat(prompt)
                    .getResults()
                    .get(0)
                    .getOutput()
                    .getContent(); // Extract the response content
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading the vulnerability file.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing AI response.";
        }
    }
}
