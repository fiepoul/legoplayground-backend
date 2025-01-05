package com.legoassistant.legoassibackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AzurePredictionResponse {

    @JsonProperty("predictions")
    private List<PredictedLegoPiece> predictions;

    public List<PredictedLegoPiece> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictedLegoPiece> predictions) {
        this.predictions = predictions;
    }

    public List<PredictedLegoPiece> filterPredictionsByProbability(double threshold) {
        if (predictions == null) {
            return List.of();
        }
        return predictions.stream()
                .filter(prediction -> prediction.getProbability() > threshold)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "AzurePredictionResponse{" +
                "predictions=" + predictions +
                '}';
    }
}



