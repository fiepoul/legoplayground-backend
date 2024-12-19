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

    public List<LegoPiece> analyzeImageFile(MultipartFile file) {
        return azureService.predictFromFile(file); // Hent og map klodselisten
    }

    public String generateRecipe(List<LegoPiece> legoPieces) {
        return openAIService.getLegoRecipe(legoPieces); // Send listen til OpenAI for opskriften
    }
}


