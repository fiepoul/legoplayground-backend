package com.legoassistant.legoassibackend.dto;

public class Choice {
    private int index;
    private Message message;

    // Getters og setters
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
