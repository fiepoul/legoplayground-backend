package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import com.legoassistant.legoassibackend.mapper.LegoPieceMapper;
import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AzureService {

    private final WebClient azureWebClient;

    public AzureService(WebClient azureWebClient) {
        this.azureWebClient = azureWebClient;
    }

    public List<LegoPiece> predictFromFile(MultipartFile file) {
        String endpoint = "customvision/v3.0/Prediction/b27c8355-bb16-4a10-b060-aca7f168ab61/detect/iterations/Iteration6/image";

        try {
            // Send billede til Azure API
            AzurePredictionResponse response = azureWebClient.post()
                    .uri(endpoint)
                    .header("Content-Type", "application/octet-stream")
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .bodyToMono(AzurePredictionResponse.class)
                    .block();

            if (response == null || response.getPredictions() == null || response.getPredictions().isEmpty()) {
                throw new RuntimeException("Azure returned no predictions.");
            }

            // Debug: Log rå Azure predictions
            System.out.println("Raw Azure Predictions: " + response.getPredictions());

            // Filtrér kun resultater over sandsynlighedstærsklen
            double threshold = 0.9;
            List<PredictedLegoPiece> filteredPredictions = response.getPredictions().stream()
                    .filter(prediction -> prediction.getProbability() >= threshold)
                    .toList(); // Brug kun filtrerede resultater

            // Debug: Log filtrerede predictions
            System.out.println("Filtered Predictions: " + filteredPredictions);

            // Mapper kun de filtrerede resultater
            List<LegoPiece> mappedLegoPieces = LegoPieceMapper.mapToLegoPieces(filteredPredictions);

            // Debug: Log mappede Lego stykker
            System.out.println("Mapped Lego Pieces: " + mappedLegoPieces);

            return mappedLegoPieces;
        } catch (Exception e) {
            throw new RuntimeException("Error sending file to Azure: " + e.getMessage());
        }
    }
}




