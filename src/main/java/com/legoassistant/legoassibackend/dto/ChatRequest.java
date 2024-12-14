package com.legoassistant.legoassibackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatRequest {

    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<Message> messages;

    @JsonProperty("n")
    private int n;

    @JsonProperty("temperature")
    private int temperature;

    @JsonProperty("max_tokens")
    private int maxTokens;

    public ChatRequest(String model, List<Message> messages, int n, int temperature, int maxTokens) {
        this.model = model;
        this.messages = messages;
        this.n = n;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }
}


