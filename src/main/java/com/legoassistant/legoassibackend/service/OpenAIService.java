package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.ChatRequest;
import com.legoassistant.legoassibackend.dto.ChatResponse;
import com.legoassistant.legoassibackend.dto.Message;
import com.legoassistant.legoassibackend.model.LegoPiece;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OpenAIService {

    private final WebClient openAiWebClient;

    public OpenAIService(@Qualifier("openAiWebClient") WebClient openAiWebClient) {
        this.openAiWebClient = openAiWebClient;
    }

    public String getLegoRecipe(List<LegoPiece> legoPieces) {
        // Byg beskedindholdet fra den færdige liste
        StringBuilder legoList = new StringBuilder("Here is the list of LEGO pieces:\n");
        for (LegoPiece piece : legoPieces) {
            legoList.append("- ").append(piece.getQuantity()).append(" x ").append(piece.getName()).append("\n");
        }

        List<Message> messages = List.of(
                new Message("system", "You are a LEGO building assistant. Generate a detailed step-by-step LEGO structure recipe based on this list."),
                new Message("user", legoList.toString())
        );

        // Send forespørgsel til OpenAI
        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages, 1, 1, 1000);
        ChatResponse response = openAiWebClient.post()
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }

        return "Unable to generate LEGO recipe.";
    }
}



