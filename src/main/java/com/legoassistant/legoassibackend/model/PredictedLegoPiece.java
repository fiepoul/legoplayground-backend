package com.legoassistant.legoassibackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictedLegoPiece {

    @JsonProperty("tagName")
    private String name;

    @JsonProperty("probability")
    private float probability;

    public String getTagName() {
        return name;
    }

    public void setTagName(String name) {
        this.name = name;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
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
