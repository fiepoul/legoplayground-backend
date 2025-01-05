package com.legoassistant.legoassibackend.mapper;

import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;

import java.util.List;
import java.util.stream.Collectors;

public class LegoPieceMapper {

    public static List<LegoPiece> mapToLegoPieces(List<PredictedLegoPiece> predictions) {
        return predictions.stream()
                .filter(prediction -> prediction.getTagName() != null)
                .collect(Collectors.groupingBy(PredictedLegoPiece::getTagName, Collectors.counting())) // Group and counting
                .entrySet()
                .stream()
                .map(entry -> new LegoPiece(entry.getKey(), entry.getValue().intValue())) // Map to LegoPiece
                .collect(Collectors.toList()); // collect list
    }
}

