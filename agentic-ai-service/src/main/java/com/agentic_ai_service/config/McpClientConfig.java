package com.agentic_ai_service.config;

import java.util.List;

import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;

@Configuration
public class McpClientConfig {

    @Bean
    public List<McpSyncClient> mcpClients(ObjectProvider<McpClient.SyncSpec> specs) {
        // Spring AI auto-creates clients from the YAML above.
        // You can wrap them with resilience if needed.
        return specs.stream().map(McpClient.SyncSpec::build).toList();
    }

    @Bean
    public ToolCallbackProvider mcpToolCallbackProvider(List<McpSyncClient> clients) {
        return new SyncMcpToolCallbackProvider(clients);
        // This automatically registers all tools from all MCP servers
        // and prefixes tool names with the connection name to avoid collisions.
    }
}

