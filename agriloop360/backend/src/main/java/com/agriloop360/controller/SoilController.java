package com.agriloop360.controller;

import com.agriloop360.entity.SoilReading;
import com.agriloop360.service.SoilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soil")
public class SoilController {

    private final SoilService soilService;

    public SoilController(SoilService soilService) {
        this.soilService = soilService;
    }

    @GetMapping("/status")
    public ResponseEntity<SoilReading> getLatestStatus(@RequestParam(required = false) Long cropId) {
        return ResponseEntity.ok(soilService.getLatestReading(cropId));
    }

    @GetMapping("/readings")
    public ResponseEntity<List<SoilReading>> getReadings(@RequestParam(required = false) Long cropId) {
        return ResponseEntity.ok(soilService.getHistoricalReadings(cropId));
    }

    @PostMapping("/readings")
    public ResponseEntity<SoilReading> recordReading(@RequestBody SoilReading reading,
                                                      @RequestParam(required = false) Long cropId) {
        return ResponseEntity.ok(soilService.recordSoilReading(reading, cropId));
    }
}
