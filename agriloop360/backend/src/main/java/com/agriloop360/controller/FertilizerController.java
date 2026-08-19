package com.agriloop360.controller;

import com.agriloop360.entity.FertilizerRecommendation;
import com.agriloop360.service.FertilizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fertilizer")
public class FertilizerController {

    private final FertilizerService fertilizerService;

    public FertilizerController(FertilizerService fertilizerService) {
        this.fertilizerService = fertilizerService;
    }

    @PostMapping("/recommendation")
    public ResponseEntity<FertilizerRecommendation> generateRecommendation(@RequestParam Long cropId) {
        return ResponseEntity.ok(fertilizerService.generateRecommendation(cropId));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<FertilizerRecommendation>> getRecommendations() {
        return ResponseEntity.ok(fertilizerService.getAllRecent());
    }

    @GetMapping("/crop/{cropId}")
    public ResponseEntity<FertilizerRecommendation> getLatestForCrop(@PathVariable Long cropId) {
        return ResponseEntity.ok(fertilizerService.getLatestByCrop(cropId));
    }
}
