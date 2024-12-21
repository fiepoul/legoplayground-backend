package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import com.legoassistant.legoassibackend.mapper.LegoPieceMapper;
import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AzureService {

    private static final Logger logger = LoggerFactory.getLogger(AzureService.class);
    private static final double DEFAULT_THRESHOLD = 0.9;
    private static final String ENDPOINT = "customvision/v3.0/Prediction/b27c8355-bb16-4a10-b060-aca7f168ab61/detect/iterations/Iteration6/image";

    private final WebClient azureWebClient;

    public AzureService(WebClient azureWebClient) {
        this.azureWebClient = azureWebClient;
    }

    public List<LegoPiece> predictFromFile(MultipartFile file) {
        try {
            validateFile(file);

            AzurePredictionResponse response = callAzureApi(file);

            List<PredictedLegoPiece> filteredPredictions = filterPredictions(response, DEFAULT_THRESHOLD);

            return LegoPieceMapper.mapToLegoPieces(filteredPredictions);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during Azure prediction: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing the file: " + e.getMessage(), e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty or null.");
        }
    }

    private AzurePredictionResponse callAzureApi(MultipartFile file) {
        try {
            return azureWebClient.post()
                    .uri(ENDPOINT)
                    .header("Content-Type", "application/octet-stream")
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .bodyToMono(AzurePredictionResponse.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to call Azure API: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to call Azure API: " + e.getMessage(), e);
        }
    }

    private List<PredictedLegoPiece> filterPredictions(AzurePredictionResponse response, double threshold) {
        if (response == null || response.getPredictions() == null || response.getPredictions().isEmpty()) {
            logger.warn("Azure returned no predictions or an empty response.");
            throw new RuntimeException("Azure returned no predictions.");
        }

        return response.getPredictions().stream()
                .filter(prediction -> prediction.getProbability() >= threshold)
                .toList();
    }
}




