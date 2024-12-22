package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AzureApiClient {

    private static final String ENDPOINT = "customvision/v3.0/Prediction/b27c8355-bb16-4a10-b060-aca7f168ab61/detect/iterations/Iteration6/image";
    private final WebClient azureWebClient;

    public AzureApiClient(WebClient azureWebClient) {
        this.azureWebClient = azureWebClient;
    }

    public AzurePredictionResponse callAzureApi(MultipartFile file) {
        try {
            return azureWebClient.post()
                    .uri(ENDPOINT)
                    .header("Content-Type", "application/octet-stream")
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .bodyToMono(AzurePredictionResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Azure API: " + e.getMessage(), e);
        }
    }
}

