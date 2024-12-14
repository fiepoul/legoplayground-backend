package com.legoassistant.legoassibackend.mapper;

import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;

import java.util.*;
import java.util.stream.Collectors;

public class LegoPieceMapper {

    public static List<LegoPiece> mapToLegoPieces(List<PredictedLegoPiece> predictions) {
        return predictions.stream()
                .collect(Collectors.groupingBy(PredictedLegoPiece::getName, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new LegoPiece(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());
    }
}
