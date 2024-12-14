package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.ChatRequest;
import com.legoassistant.legoassibackend.dto.ChatResponse;
import com.legoassistant.legoassibackend.dto.Message;
import com.legoassistant.legoassibackend.model.LegoPiece;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OpenAIService {

    private final WebClient webClient;

    public OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getLegoIdeas(List<LegoPiece> legoPieces) {
        // 1. Byg beskedindhold til OpenAI baseret på Azure-resultater
        StringBuilder legoList = new StringBuilder("Here is the list of LEGO pieces detected:\n");
        for (LegoPiece piece : legoPieces) {
            legoList.append("- ").append(piece.getQuantity()).append(" x ").append(piece.getName()).append("\n");
        }

        // 2. Definér roller og instruktioner som hardkodede beskeder
        List<Message> messages = List.of(
                new Message("system", "You are a LEGO building assistant. Your job is to provide two things: (1) the full list of LEGO pieces detected and (2) a detailed recipe for a LEGO structure that can be built with these pieces."),
                new Message("user", legoList.toString())
        );

        // 3. Opret ChatRequest til OpenAI
        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages, 1, 1, 1000);

        // 4. Send forespørgsel til OpenAI og håndter svar
        ChatResponse response = webClient.post()
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }

        return "Unable to generate LEGO ideas.";
    }
}



