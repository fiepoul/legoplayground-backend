package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.service.LegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lego")
@CrossOrigin(origins = "http://localhost:3000")
public class LegoController {

    private final LegoService legoService;

    public LegoController(LegoService legoService) {
        this.legoService = legoService;
    }

    @PostMapping("/ideas")
    public ResponseEntity<String> generateLegoIdeas(@RequestParam("imageUrl") String imageUrl) {
        try {
            // Valider imageUrl
            if (imageUrl == null || imageUrl.isBlank()) {
                return ResponseEntity.badRequest().body("Image URL cannot be empty");
            }

            // Kald LegoService for at generere idéer
            String ideas = legoService.analyzeImageAndGenerateIdeas(imageUrl);

            return ResponseEntity.ok(ideas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

