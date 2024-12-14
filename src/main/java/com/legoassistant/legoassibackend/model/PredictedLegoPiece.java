package com.legoassistant.legoassibackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictedLegoPiece {

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
