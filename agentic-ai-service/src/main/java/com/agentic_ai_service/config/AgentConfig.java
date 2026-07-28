package com.agentic_ai_service.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider; // <--- ADD THIS IMPORT
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public ChatClient agentChatClient(
            OllamaChatModel chatModel, 
            SyncMcpToolCallbackProvider mcpToolProvider) { // <--- USE SyncMcpToolCallbackProvider
        
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                    You are an autonomous Agentic Assistant for enterprise operations.
                    You have access to connected microservices via MCP tools.
                    When asked to perform multi-step operations, evaluate which microservice tools
                    you need to call, execute them sequentially, and construct a concise final response.
                    """)
                .defaultTools(mcpToolProvider) // Passes discovered MCP tools
                .build();
    }
}
