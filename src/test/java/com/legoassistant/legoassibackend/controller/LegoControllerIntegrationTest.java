package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.service.AzureService;
import com.legoassistant.legoassibackend.service.LegoService;
import com.legoassistant.legoassibackend.service.OpenAIService;
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

    @Test
    void testUploadImage_withValidFile() throws Exception {
        // Mock et eksempel på returneret LEGO-stykker
        List<LegoPiece> mockPieces = List.of(
                new LegoPiece("Brick", 1),
                new LegoPiece("Plate", 1)
        );

        // Mock kald til LegoService
        when(legoService.analyzeImageFile(Mockito.any())).thenReturn(mockPieces);
        when(legoService.generateRecipe(mockPieces)).thenReturn("Mock Recipe");

        // Mock en fil
        MockMultipartFile file = new MockMultipartFile(
                "image", // Parameter-navnet i controlleren
                "test.jpg", // Filnavnet
                MediaType.IMAGE_JPEG_VALUE, // Content-Type
                "mock file content".getBytes() // Filindhold
        );

        // Udfør testen
        mockMvc.perform(multipart("/api/lego/ideas").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legoList").isArray())
                .andExpect(jsonPath("$.legoList[0].name").value("Brick")) // Opdateret til 'name'
                .andExpect(jsonPath("$.legoList[1].name").value("Plate")) // Opdateret til 'name'
                .andExpect(jsonPath("$.recipe").value("Mock Recipe"));
    }


    @Test
    void testUploadImage_withEmptyFile() throws Exception {
        // Mock en tom fil
        MockMultipartFile emptyFile = new MockMultipartFile(
                "image",
                "empty.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/api/lego/ideas").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("File is empty"));
    }
}
