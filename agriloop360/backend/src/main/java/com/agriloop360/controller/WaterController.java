package com.agriloop360.controller;

import com.agriloop360.entity.WaterReading;
import com.agriloop360.service.WaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/water")
public class WaterController {

    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @GetMapping("/status")
    public ResponseEntity<WaterReading> getLatestStatus() {
        return ResponseEntity.ok(waterService.getLatestReading());
    }

    @GetMapping("/readings")
    public ResponseEntity<List<WaterReading>> getReadings() {
        return ResponseEntity.ok(waterService.getHistoricalReadings());
    }

    @PostMapping("/readings")
    public ResponseEntity<WaterReading> createReading(@RequestBody WaterReading reading) {
        return ResponseEntity.ok(waterService.saveReading(reading));
    }
}
