import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class AgentService {

    private final ChatClient chatClient;

    public AgentService(ChatClient.Builder builder,
                        ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = builder
                .defaultSystem("""
                    You are a helpful enterprise assistant.
                    You have access to tools from multiple microservices.
                    Always use tools when you need real data.
                    Be concise and cite which service you used.
                    """)
                .defaultTools(toolCallbackProvider)
                .build();
    }

    public String chat(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }

    // For streaming
    public Flux<String> chatStream(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .stream()
                .content();
    }
}
