package com.legoassistant.legoassibackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictedLegoPiece {

    @JsonProperty("name")
    private String name;

    @JsonProperty("probability")
    private String probability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    @Override
public String toString() {
    return "PredictedLegoPiece{" +
            "name='" + name + '\'' +
            ", probability=" + probability +
            '}';
}

}
