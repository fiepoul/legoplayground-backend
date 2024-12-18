package com.legoassistant.legoassibackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import com.legoassistant.legoassibackend.mapper.LegoPieceMapper;
import com.legoassistant.legoassibackend.model.LegoPiece;
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
            // Send filen til Azure Custom Vision API
            AzurePredictionResponse response = azureWebClient.post()
                    .uri(endpoint)
                    .header("Content-Type", "application/octet-stream")
                    .bodyValue(file.getBytes()) // Send billedet som byte-array
                    .retrieve()
                    .bodyToMono(AzurePredictionResponse.class) // Deserialiser JSON til DTO
                    .block();

            // Debugging for at se hele svaret
            System.out.println("Full Azure Response: " + response);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Full Azure API Response: " + mapper.writeValueAsString(response));

            // Tjek, om response er null eller har tomme forudsigelser
            if (response == null || response.getPredictions() == null || response.getPredictions().isEmpty()) {
                throw new RuntimeException("Azure returned no predictions.");
            }

            System.out.println("Azure Predictions: " + response.getPredictions());

            // Mapper predictions til en List<LegoPiece>
            return LegoPieceMapper.mapToLegoPieces(response.getPredictions());
        } catch (Exception e) {
            throw new RuntimeException("Error sending file to Azure: " + e.getMessage());
        }
    }
}


