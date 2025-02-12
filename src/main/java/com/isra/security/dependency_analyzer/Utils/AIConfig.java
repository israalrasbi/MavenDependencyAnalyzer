package com.isra.security.dependency_analyzer.Utils;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {
/*
    @Bean
    public ChatClient chatClient() {
        String apiKey = System.getenv("OPENAI_API_KEY"); // Or read from application.properties
        OpenAiChatModel model = new OpenAiChatModel(apiKey);
        return new ChatClient(model); // Use ChatClient directly
    }*/
}
