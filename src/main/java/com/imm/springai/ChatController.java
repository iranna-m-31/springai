package com.imm.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates the ChatClient fluent API in its most minimal form —
 * one user message in, one text response out, no memory or tools.
 * See Spring AI reference: "ChatClient" section.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * @param userInput free-text prompt from the caller
     * @param system    optional system prompt to set behavior
     * @param history   optional JSON string of conversation history
     * @return the raw text completion from the configured chat model
     * See Spring AI reference: "ChatClient" section
     */
    @GetMapping("/ai")
    String generation(
            @RequestParam String userInput,
            @RequestParam(required = false) String system,
            @RequestParam(required = false) String history) {

        // Build prompt with system message if provided
        if (system != null && !system.isEmpty()) {
            return chatClient.prompt()
                    .system(system)
                    .user(userInput)
                    .call()
                    .content();
        } else {
            return chatClient.prompt()
                    .user(userInput)
                    .call()
                    .content();
        }
    }
}