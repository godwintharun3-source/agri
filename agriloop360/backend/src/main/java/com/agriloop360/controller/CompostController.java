package com.agriloop360.controller;

import com.agriloop360.entity.CompostBatch;
import com.agriloop360.entity.CompostReading;
import com.agriloop360.enumtype.CompostStage;
import com.agriloop360.service.CompostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compost")
public class CompostController {

    private final CompostService compostService;

    public CompostController(CompostService compostService) {
        this.compostService = compostService;
    }

    @GetMapping
    public ResponseEntity<List<CompostBatch>> getAllBatches() {
        return ResponseEntity.ok(compostService.getAllBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompostBatch> getBatchById(@PathVariable Long id) {
        return ResponseEntity.ok(compostService.getBatchById(id));
    }

    @PostMapping
    public ResponseEntity<CompostBatch> createBatch(@RequestBody CompostBatch batch) {
        return ResponseEntity.ok(compostService.createBatch(batch));
    }

    @PutMapping("/{id}/stage")
    public ResponseEntity<CompostBatch> updateStage(@PathVariable Long id, @RequestParam CompostStage stage) {
        return ResponseEntity.ok(compostService.updateBatchStage(id, stage));
    }

    @GetMapping("/{id}/readings")
    public ResponseEntity<List<CompostReading>> getBatchReadings(@PathVariable Long id) {
        return ResponseEntity.ok(compostService.getBatchReadings(id));
    }
}
