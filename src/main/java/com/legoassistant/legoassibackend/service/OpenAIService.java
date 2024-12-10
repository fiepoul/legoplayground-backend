package com.legoassistant.legoassibackend.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
    public String getLegoIdeas(String azureResult) {
        // Byg en prompt baseret på Azure-resultatet og send til OpenAI
        return "OpenAI-generated LEGO idea";
    }
}
