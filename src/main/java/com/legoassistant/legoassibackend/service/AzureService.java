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

    private final AzureApiClient azureApiClient;
    private final ImagePredictionFilter predictionFilter;

    public AzureService(AzureApiClient azureApiClient, ImagePredictionFilter predictionFilter) {
        this.azureApiClient = azureApiClient;
        this.predictionFilter = predictionFilter;
    }

    public List<LegoPiece> predictFromFile(MultipartFile file) {
        try {
            FileValidator.validate(file);

            AzurePredictionResponse response = azureApiClient.callAzureApi(file);

            List<PredictedLegoPiece> filteredPredictions = predictionFilter.filter(response, DEFAULT_THRESHOLD);

            return LegoPieceMapper.mapToLegoPieces(filteredPredictions);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during Azure prediction: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing the file: " + e.getMessage(), e);
        }
    }
}





