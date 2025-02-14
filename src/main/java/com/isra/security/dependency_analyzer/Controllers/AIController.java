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
    private ChatClient chatClient;

    public AIController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/{chat}")
    public ResponseEntity<String> promptWithPathVariable(@PathVariable String chat) {
        try {
            String response = chatClient
                    .prompt(chat)
                    .call()
                    .content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stream/{chat}")
    public Flux<String> streamChat(@PathVariable String chat) {
        return chatClient
                .prompt()
                .user(chat)
                .stream()
                .content();
    }


    @Autowired
    private AIService aiService;

    @PostMapping("/analyze-vulnerabilities")
    public ResponseEntity<String> analyzeVulnerabilities(@RequestParam("filePath") String filePath) {
        try {
            // Read the content of the file from the given file path
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            // Call the AI model to analyze the vulnerabilities (just like before)
            String aiResponse = aiService.analyzeVulnerabilities(content);

            return ResponseEntity.ok(aiResponse);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
