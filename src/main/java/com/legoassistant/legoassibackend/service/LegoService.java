package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.exception.FileProcessingException;
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
        FileValidator.validate(file);
        return azureService.predictFromFile(file);
    }

    /**
     * Generates a recipe for building a LEGO structure using the given pieces.
     * @param legoPieces the list of LEGO pieces
     * @return a recipe as a string
     */
    public String generateRecipe(List<LegoPiece> legoPieces) {
        return openAIService.getLegoRecipe(legoPieces);
    }
}



