package com.legoassistant.legoassibackend.controller;

import com.legoassistant.legoassibackend.model.LegoPiece;
import com.legoassistant.legoassibackend.service.LegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lego")
@CrossOrigin(origins = "http://localhost:3000")
public class LegoController {

    private final LegoService legoService;

    public LegoController(LegoService legoService) {
        this.legoService = legoService;
    }

    @PostMapping("/ideas")
    public ResponseEntity<Map<String, Object>> generateLegoIdeas(@RequestParam("image") MultipartFile file) {
        try {
            System.out.println("Received file: " + file.getOriginalFilename());
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }

            List<LegoPiece> legoPieces = legoService.analyzeImageFile(file); // Klodseliste fra Azure
            System.out.println("Detected LEGO pieces: " + legoPieces);
            String recipe = legoService.generateRecipe(legoPieces);
            System.out.println("Generated recipe: " + recipe);// Opskrift fra OpenAI

            return ResponseEntity.ok(Map.of(
                    "legoList", legoPieces,
                    "recipe", recipe
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

