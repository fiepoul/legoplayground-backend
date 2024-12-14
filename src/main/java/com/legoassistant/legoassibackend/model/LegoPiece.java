package com.legoassistant.legoassibackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LegoPiece {

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private int quantity;

    public LegoPiece() {
    }

    public LegoPiece(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}