package com.isra.security.dependency_analyzer.Utils;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AIConfig {
    /*
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.model}")
    private String model;

    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder()
                .apiKey(apiKey)
                .model(model)
                .build();
    }*/

}
