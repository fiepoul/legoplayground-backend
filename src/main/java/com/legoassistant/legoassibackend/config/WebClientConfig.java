package com.legoassistant.legoassibackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${azure.cognitive.key}")
    private String azureKey;

    @Value("${azure.endpoint}")
    private String azureEndpoint;

    @Value("${openai.api.key}")
    private String openAiKey;

    @Value("${openai.endpoint}")
    private String openAiEndpoint;

    @Bean
    public WebClient azureWebClient() {
        return WebClient.builder()
                .baseUrl(azureEndpoint)
                .defaultHeader("Prediction-Key", azureKey) // Azure-specific header
                .build();
    }

    @Bean
    public WebClient openAiWebClient() {
        return WebClient.builder()
                .baseUrl(openAiEndpoint)
                .defaultHeader("Authorization", "Bearer " + openAiKey) // OpenAI-specific header
                .build();
    }
}
