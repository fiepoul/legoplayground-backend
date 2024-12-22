package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImagePredictionFilter {

    public List<PredictedLegoPiece> filter(AzurePredictionResponse response, double threshold) {
        if (response == null || response.getPredictions() == null || response.getPredictions().isEmpty()) {
            throw new RuntimeException("Azure returned no predictions.");
        }

        return response.getPredictions().stream()
                .filter(prediction -> prediction.getProbability() >= threshold)
                .toList();
    }
}

