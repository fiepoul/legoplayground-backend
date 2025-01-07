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
        // build message
        StringBuilder legoList = new StringBuilder("Here is the list of LEGO pieces:\n");
        for (LegoPiece piece : legoPieces) {
            legoList.append("- ").append(piece.getQuantity()).append(" x ").append(piece.getName()).append("\n");
        }

        List<Message> messages = List.of(
                new Message("system", "You are a LEGO building assistant. Your task is to generate a recipe for building a LEGO structure using the given LEGO pieces. Follow these rules strictly:\n" +
                        "\n" +
                        "1. Start the response with a short(maximum two words) and fun headline for the recipe. Format it as follows: **Headline Name**\n" +
                        "2. Do not include any additional descriptions or explanations outside of the steps.\n" +
                        "3. Write each step as one or two sentence and make them inviting. Begin each step with 'Step X:', where X is the step number, followed by the instructions. Each step should be concise in structure. The amount of steps are four\n" +
                        "4. Do not include any other text, comments, or unnecessary details."),
                new Message("user", legoList.toString())
        );

        // request to OpenAI
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



