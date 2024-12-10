package com.legoassistant.legoassibackend.service;

import org.springframework.stereotype.Service;

@Service
public class AzureService {
    public String analyzeImage(byte[] imageBytes) {
        // Konfigurer og send billedet til Azure Custom Vision
        // (brug f.eks. REST API eller Azure SDK)
        return "Azure result (e.g., detected LEGO pieces)";
    }
}
