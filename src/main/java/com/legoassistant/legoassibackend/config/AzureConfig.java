package com.legoassistant.legoassibackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AzureConfig {
    @Value("${azure.cognitive.key}")
    private String azureKey;

    @Value("${azure.endpoint}")
    private String endpoint;

    @Bean
    public WebClient azureWebClient() {
        return WebClient.builder()
                .baseUrl(endpoint)
                .defaultHeader("Prediction-Key", azureKey) // Azure Custom Vision-specific header
                .build();
    }
}
