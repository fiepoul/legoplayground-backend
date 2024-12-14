package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.service.LegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lego")
@CrossOrigin(origins = "http://localhost:3000")
public class LegoController {

    private final LegoService legoService;

    public LegoController(LegoService legoService) {
        this.legoService = legoService;
    }

    @PostMapping("/ideas")
    public ResponseEntity<String> generateLegoIdeas(@RequestParam("image") MultipartFile file) {
        try {
            // Valider filen
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Kald LegoService for at generere idéer
            String ideas = legoService.analyzeImageFileAndGenerateIdeas(file);

            return ResponseEntity.ok(ideas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

