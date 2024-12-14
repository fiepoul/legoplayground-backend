package com.legoassistant.legoassibackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.legoassistant.legoassibackend.model.LegoPiece;

import java.util.List;

public class CustomVisionResponse {
    @JsonProperty("lego_pieces")
    private List<LegoPiece> legoPieces;

    public CustomVisionResponse(List<LegoPiece> legoPieces) {
        this.legoPieces = legoPieces;
    }

    public List<LegoPiece> getLegoPieces() {
        return legoPieces;
    }

    public void setLegoPieces(List<LegoPiece> legoPieces) {
        this.legoPieces = legoPieces;
    }
}
