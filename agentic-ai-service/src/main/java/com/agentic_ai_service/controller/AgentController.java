package com.agentic_ai_service.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.agentic_ai_service.service.AgentService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    public String chat(@RequestBody Map<String, String> body) {
        return agentService.chat(body.get("message"));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String message) {
        return agentService.chatStream(message);
    }
}