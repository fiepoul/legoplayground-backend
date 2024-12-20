package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.service.AzureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UploadController {

    private final AzureService azureService;

    public UploadController(AzureService azureService) {
        this.azureService = azureService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("image") MultipartFile file) {
        try {
            // Valider filen
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Send filen til Azure-service for analyse
            List<LegoPiece> legoPieces = azureService.predictFromFile(file);

            // Konverter listen til en String
            String result = legoPieces.stream()
                    .map(piece -> piece.getQuantity() + " x " + piece.getName())
                    .collect(Collectors.joining("\n"));

            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename() + "\n" + result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}

