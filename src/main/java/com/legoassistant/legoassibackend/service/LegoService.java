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

    /**
     * Analyzes an image file to extract LEGO pieces using Azure's prediction service.
     * @param file the image file to analyze
     * @return a list of LEGO pieces
     */
    public List<LegoPiece> analyzeImageFile(MultipartFile file) {
        validateFile(file);
        return azureService.predictFromFile(file);
    }

    /**
     * Generates a recipe for building a LEGO structure using the given pieces.
     * @param legoPieces the list of LEGO pieces
     * @return a recipe as a string
     */
    public String generateRecipe(List<LegoPiece> legoPieces) {
        if (legoPieces == null || legoPieces.isEmpty()) {
            throw new IllegalArgumentException("LEGO pieces list cannot be null or empty.");
        }
        return openAIService.getLegoRecipe(legoPieces);
    }

    /**
     * Validates the input file to ensure it's not empty.
     * @param file the file to validate
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty or null.");
        }
    }
}



