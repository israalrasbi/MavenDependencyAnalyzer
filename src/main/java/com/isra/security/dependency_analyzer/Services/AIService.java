package com.isra.security.dependency_analyzer.Services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AIService {
    private ChatClient chatClient;

    public AIService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
    public String analyzeVulnerabilities(String jsonContent) {
        // Send the JSON content to the AI model for analysis
        String response = chatClient
                .prompt("Analyze the following vulnerabilities and provide feedback: " + jsonContent)
                .call()
                .content();
        return response;
    }
}
