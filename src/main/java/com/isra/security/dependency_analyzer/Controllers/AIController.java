package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "ai")
public class AIController {

    @Autowired
    private AIService aiService;
/*
    @GetMapping("/analyze")
    public String analyzeVulnerabilities() {
        return aiService.analyzeVulnerabilities();
    }*/

    private final ChatClient chatClient;

    public AIController(ChatClient chatClient) {
        this.chatClient =chatClient;
    }

    @GetMapping("/analyze")
    public String analyze(@RequestParam(value = "message", defaultValue = "Tell me the best programming language") String message) {
        return aiService.runDeepseekR1(message);
    }
}
