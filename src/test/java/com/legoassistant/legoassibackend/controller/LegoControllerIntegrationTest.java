package com.legoassistant.legoassibackend.controller;

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

    @Test
    void testUploadImage_withEmptyFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "image",
                "empty.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/api/lego/ideas").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Uploaded file cannot be null or empty."));
    }

    @Test
    void testUploadImage_withInvalidFileType() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile(
                "image",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is a text file".getBytes()
        );

        mockMvc.perform(multipart("/api/lego/ideas").file(invalidFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Uploaded file is not a valid image."));
    }

    @Test
    void testUploadImage_withIncorrectMimeType() throws Exception {
        MockMultipartFile fakeJpegFile = new MockMultipartFile(
                "image",
                "fake.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "This is not a real image".getBytes()
        );

        mockMvc.perform(multipart("/api/lego/ideas").file(fakeJpegFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Uploaded file is not a valid image."));
    }

    @Test
    void testUploadImage_withCorruptImage() throws Exception {
        MockMultipartFile corruptImageFile = new MockMultipartFile(
                "image",
                "corrupt.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "corrupt image content".getBytes()
        );

        mockMvc.perform(multipart("/api/lego/ideas").file(corruptImageFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Uploaded file is not a valid image."));
    }
}

