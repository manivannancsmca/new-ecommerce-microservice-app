package com.agentic_ai_service.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController {

    private final ChatClient chatClient;

    public AgentController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/prompt")
    public String executeAgentTask(@RequestBody String userPrompt) {
        return chatClient.prompt(userPrompt)
                .call()
                .content();
    }
}