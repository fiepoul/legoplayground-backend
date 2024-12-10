package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.dto.ChatRequest;
import com.legoassistant.legoassibackend.dto.ChatResponse;
import com.legoassistant.legoassibackend.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lego")
@CrossOrigin(origins = "http://localhost:3000")
public class LegoController {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public LegoController() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .build();
    }

    @PostMapping("/ideas")
    public String getLegoIdeas(@RequestBody String userInput) {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "You are a creative idea machine helping a LEGO builder. you can only write five words"));
        messages.add(new Message("user", "I have the following LEGO pieces: " + userInput + ". What can I build?"));
        chatRequest.setMessages(messages);
        chatRequest.setN(1);
        chatRequest.setTemperature(1);
        chatRequest.setMaxTokens(30);

        try {
            ChatResponse response = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "I'm not sure what you can build with those pieces. Try adding more detail!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while communicating with the OpenAI API. Please try again later.";
        }
    }
}
