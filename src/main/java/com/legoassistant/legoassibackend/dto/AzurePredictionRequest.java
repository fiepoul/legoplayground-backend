package com.legoassistant.legoassibackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AzurePredictionRequest {

    @JsonProperty("Url")
    private String url;

    public AzurePredictionRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

