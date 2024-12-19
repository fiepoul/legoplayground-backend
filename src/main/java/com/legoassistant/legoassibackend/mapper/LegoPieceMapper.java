package com.legoassistant.legoassibackend.mapper;

import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;

import java.util.List;
import java.util.stream.Collectors;

public class LegoPieceMapper {

    public static List<LegoPiece> mapToLegoPieces(List<PredictedLegoPiece> predictions) {
        // Gruppér efter tagName og tæl forekomsterne
        return predictions.stream()
                .filter(prediction -> prediction.getTagName() != null) // Filtrér null-navne
                .collect(Collectors.groupingBy(PredictedLegoPiece::getTagName, Collectors.counting())) // Gruppér og tæl
                .entrySet()
                .stream()
                .map(entry -> new LegoPiece(entry.getKey(), entry.getValue().intValue())) // Map til LegoPiece
                .collect(Collectors.toList()); // Saml som en liste
    }
}

