package com.isra.security.dependency_analyzer.Services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AIService {
    private final ChatClient chatClient;

    @Autowired
    public AIService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
    public String analyzeVulnerabilities(String jsonContent) {
        return chatClient
                .prompt("Analyze the following vulnerabilities and provide feedback: " + jsonContent)
                .call()
                .content();
    }

    public String readVulnerabilitiesFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
