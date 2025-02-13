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
    /*
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
    }*/
    public String analyzeVulnerabilities(String jsonContent) {
        // Send the JSON content to the AI model for analysis
        ChatClient chatClient = new ChatClient() {
            @Override
            public ChatClientRequestSpec prompt() {
                return null;
            }

            @Override
            public ChatClientRequestSpec prompt(String content) {
                return null;
            }

            @Override
            public ChatClientRequestSpec prompt(Prompt prompt) {
                return null;
            }

            @Override
            public Builder mutate() {
                return null;
            }
        };
        String response = chatClient
                .prompt("Analyze the following vulnerabilities and provide feedback: " + jsonContent)
                .call()
                .content();
        return response;
    }





}
