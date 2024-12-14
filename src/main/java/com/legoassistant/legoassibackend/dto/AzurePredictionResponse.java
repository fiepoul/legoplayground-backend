package com.legoassistant.legoassibackend.dto;

import com.legoassistant.legoassibackend.model.LegoPiece;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;

import java.util.List;

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

    @Override
    public String toString() {
        return "AzurePredictionResponse{" +
                "predictions=" + predictions +
                '}';
    }
}

