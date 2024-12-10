package com.legoassistant.legoassibackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AzureService {

    private final WebClient webClient;

    public AzureService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://lego-brick-predicter.cognitiveservices.azure.com")
                .defaultHeader("Prediction-Key", "haCQnygPHcSmoPVq07s0PdYMNlowaGIUU50G78PbF8khNuLJzYrTJQQJ99ALAC5RqLJXJ3w3AAAIACOG5XoD")
                .build();
    }

    public String predictFromUrl(String imageUrl) {
        String endpoint = "/customvision/v3.0/Prediction/b27c8355-bb16-4a10-b060-aca7f168ab61/detect/iterations/Iteration6/url";
        return webClient.post()
                .uri(endpoint)
                .header("Content-Type", "application/json")
                .bodyValue("{\"Url\": \"" + imageUrl + "\"}")
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocking for simplicity
    }
}

