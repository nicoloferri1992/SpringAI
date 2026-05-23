package com.AI.adoptions;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
class AdoptionsController {

    private final ChatClient ai;

    AdoptionsController(JdbcClient db,
                        McpSyncClient mcpSyncClient,
                        MessageChatMemoryAdvisor messageChatMemoryAdvisor,
                        ChatClient.Builder ai,
                        DogRepository repository,
                        VectorStore vectorStore) {

        var count = db
                .sql("select count(*) from vector_store")
                .query(Integer.class)
                .single();

        if (count == 0) {
            repository.findAll().forEach(dog -> {
                var dogument = new Document("id: %s, name: %s, description: %s".formatted(
                        dog.id(), dog.name(), dog.description()
                ));
                vectorStore.add(List.of(dogument));
            });
        }

        var system = """
                You are an AI powered assistant to help people adopt a dog from the adoption agency \
                named Pooch Palace with locations in Rio de Janeiro, Mexico City, Seoul, Tokyo, \
                Singapore, Paris, Mumbai, New Delhi, Barcelona, London, and San Francisco. \
                Information about the dogs available will be presented below. \
                If there is no information, then return a polite response suggesting we don't have \
                any dogs available.
                """;

        this.ai = ai
                .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClient))
                .defaultAdvisors(messageChatMemoryAdvisor, QuestionAnswerAdvisor.builder(vectorStore).build())
                .defaultSystem(system)
                .build();
    }

    @GetMapping("/{user}/assistant")
    String inquire(@PathVariable String user, @RequestParam String question) {
        return ai
                .prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
                .call()
                .content();
    }
}
