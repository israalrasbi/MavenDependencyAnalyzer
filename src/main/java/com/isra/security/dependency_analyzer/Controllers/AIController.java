package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "ai")
public class AIController {

    /*
    @Autowired
    private AIService aiService;

    @GetMapping("/analyze")
    public String analyzeVulnerabilities() {
        return aiService.analyzeVulnerabilities();
    }

    private final ChatClient chatClient;

    public AIController(ChatClient chatClient) {
        this.chatClient =chatClient;
    }

    @GetMapping("/analyze")
    public String analyze(@RequestParam(value = "message", defaultValue = "Tell me the best programming language") String message) {
        return aiService.runDeepseekR1(message);
    }*/

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


}
