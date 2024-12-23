package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.dto.AzurePredictionResponse;
import com.legoassistant.legoassibackend.model.PredictedLegoPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImagePredictionFilterTest {

    @BeforeEach
    void setup() {
        // Set up mock environment variables or system properties
        System.setProperty("AZURE_AI_KEY", "test_key");
    }
    @Test
    void filter_validResponseWithThreshold_returnsFilteredPredictions() {
        // Arrange
        ImagePredictionFilter filter = new ImagePredictionFilter();
        AzurePredictionResponse mockResponse = new AzurePredictionResponse();
        mockResponse.setPredictions(List.of(
                new PredictedLegoPiece("Brick", 0.95f),
                new PredictedLegoPiece("Plate", 0.75f),
                new PredictedLegoPiece("Tile", 0.60f)
        ));

        double threshold = 0.80;

        // Act
        List<PredictedLegoPiece> filteredPieces = filter.filter(mockResponse, threshold);

        // Assert
        assertNotNull(filteredPieces);
        assertEquals(1, filteredPieces.size());
        assertEquals("Brick", filteredPieces.get(0).getTagName());
        assertEquals(0.95f, filteredPieces.get(0).getProbability(), 0.0001f); // tolerance
    }

    @Test
    void filter_responseWithNoPredictions_throwsRuntimeException() {
        // Arrange
        ImagePredictionFilter filter = new ImagePredictionFilter();
        AzurePredictionResponse mockResponse = new AzurePredictionResponse();
        mockResponse.setPredictions(List.of());

        double threshold = 0.80;

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> filter.filter(mockResponse, threshold));
        assertEquals("Azure returned no predictions.", exception.getMessage());
    }

    @Test
    void filter_nullResponse_throwsRuntimeException() {
        // Arrange
        ImagePredictionFilter filter = new ImagePredictionFilter();

        double threshold = 0.80;

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> filter.filter(null, threshold));
        assertEquals("Azure returned no predictions.", exception.getMessage());
    }

    @Test
    void filter_nullPredictionsInResponse_throwsRuntimeException() {
        // Arrange
        ImagePredictionFilter filter = new ImagePredictionFilter();
        AzurePredictionResponse mockResponse = new AzurePredictionResponse();
        mockResponse.setPredictions(null);

        double threshold = 0.80;

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> filter.filter(mockResponse, threshold));
        assertEquals("Azure returned no predictions.", exception.getMessage());
    }

}