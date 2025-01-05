package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.exception.FileProcessingException;
import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.service.LegoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LegoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LegoService legoService;

    // Test case: Ingen brikker fundet
    @Test
    void testUploadImage_withNoDetectedBricks() throws Exception {
        MockMultipartFile validImage = new MockMultipartFile(
                "image",
                "valid.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Valid image content".getBytes()
        );

        // Mock LegoService to simulate no detected bricks
        Mockito.when(legoService.analyzeImageFile(Mockito.any()))
                .thenThrow(new FileProcessingException("No LEGO pieces detected in the image."));

        mockMvc.perform(multipart("/api/lego/ideas").file(validImage))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("No LEGO pieces detected in the image."));
    }

    // Test case: Brikker fundet og opskrift genereret
    @Test
    void testUploadImage_withDetectedBricks() throws Exception {
        MockMultipartFile validImage = new MockMultipartFile(
                "image",
                "valid.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Valid image content".getBytes()
        );

        List<LegoPiece> legoPieces = List.of(
                new LegoPiece("Brick 1", 1),
                new LegoPiece("Brick 2", 1)
        );
        String generatedRecipe = "Build a house with 2 bricks.";

        // Mock LegoService to return detected bricks and recipe
        Mockito.when(legoService.analyzeImageFile(Mockito.any())).thenReturn(legoPieces);
        Mockito.when(legoService.generateRecipe(legoPieces)).thenReturn(generatedRecipe);

        mockMvc.perform(multipart("/api/lego/ideas").file(validImage))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legoList[0].name").value("Brick 1"))
                .andExpect(jsonPath("$.legoList[0].quantity").value(1))
                .andExpect(jsonPath("$.legoList[1].name").value("Brick 2"))
                .andExpect(jsonPath("$.legoList[1].quantity").value(1))
                .andExpect(jsonPath("$.recipe").value(generatedRecipe));
    }

    // Test case: Fil uden indhold
    @Test
    void testUploadImage_withEmptyFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "image",
                "empty.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );

        // Mock LegoService to simulate file validation failure
        Mockito.doThrow(new FileProcessingException("Uploaded file cannot be null or empty."))
                .when(legoService).analyzeImageFile(Mockito.any());

        mockMvc.perform(multipart("/api/lego/ideas").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Uploaded file cannot be null or empty."));
    }

    // Test case: Uventet fejl
    @Test
    void testUploadImage_withUnexpectedError() throws Exception {
        MockMultipartFile validImage = new MockMultipartFile(
                "image",
                "valid.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Valid image content".getBytes()
        );

        // Mock LegoService to throw a generic exception
        Mockito.doThrow(new RuntimeException("Unexpected error occurred"))
                .when(legoService).analyzeImageFile(Mockito.any());

        mockMvc.perform(multipart("/api/lego/ideas").file(validImage))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An unexpected error occurred"));
    }
}

