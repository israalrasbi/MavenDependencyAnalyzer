package com.isra.security.dependency_analyzer.Services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    @Autowired
    private ChatClient chatClient;

    private static final String FILE_PATH = "output/vulnerabilities.json";

}
