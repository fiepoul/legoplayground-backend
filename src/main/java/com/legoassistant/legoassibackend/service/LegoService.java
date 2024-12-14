package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.model.LegoPiece;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LegoService {

    private final AzureService azureService;
    private final OpenAIService openAIService;

    public LegoService(AzureService azureService, OpenAIService openAIService) {
        this.azureService = azureService;
        this.openAIService = openAIService;
    }

    public String analyzeImageFileAndGenerateIdeas(MultipartFile file) {
        // 1. Få LEGO-klodser fra AzureService
        List<LegoPiece> legoPieces = azureService.predictFromFile(file);

        // 2. Kontroller, om der blev fundet nogen LEGO-klodser
        if (legoPieces == null || legoPieces.isEmpty()) {
            throw new IllegalArgumentException("No LEGO pieces detected in the uploaded image.");
        }

        // 3. Generér LEGO-idéer fra OpenAIService
        return openAIService.getLegoIdeas(legoPieces);
    }
}

