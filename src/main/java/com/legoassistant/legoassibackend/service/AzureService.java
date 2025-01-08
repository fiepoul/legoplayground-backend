package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import com.legoassistant.legoassibackend.exception.FileProcessingException;
import com.legoassistant.legoassibackend.mapper.LegoPieceMapper;
import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class AzureService {

    private static final double DEFAULT_THRESHOLD = 0.9;

    private final AzureApiClient azureApiClient;
    private final ImagePredictionFilter predictionFilter;

    public AzureService(AzureApiClient azureApiClient, ImagePredictionFilter predictionFilter) {
        this.azureApiClient = azureApiClient;
        this.predictionFilter = predictionFilter;
    }

    public List<LegoPiece> predictFromFile(MultipartFile file) {
        AzurePredictionResponse response = azureApiClient.callAzureApi(file);

        if (response == null || response.getPredictions() == null || response.getPredictions().isEmpty()) {
            throw new FileProcessingException("No predictions found.");
        }

        List<PredictedLegoPiece> filteredPredictions = predictionFilter.filter(response, DEFAULT_THRESHOLD);

        if (filteredPredictions.isEmpty()) {
            throw new FileProcessingException("No predictions met the threshold.");
        }

        return LegoPieceMapper.mapToLegoPieces(filteredPredictions);
    }
}





