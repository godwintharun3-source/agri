package com.agriloop360.controller;

import com.agriloop360.entity.FoodStorage;
import com.agriloop360.entity.FoodStorageReading;
import com.agriloop360.service.FoodStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class FoodStorageController {

    private final FoodStorageService storageService;

    public FoodStorageController(FoodStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<FoodStorage>> getAllStorageUnits() {
        return ResponseEntity.ok(storageService.getAllStorageUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodStorage> getStorageById(@PathVariable Long id) {
        return ResponseEntity.ok(storageService.getStorageById(id));
    }

    @PostMapping
    public ResponseEntity<FoodStorage> createStorage(@RequestBody FoodStorage storage,
                                                      @RequestParam(required = false) Long cropId) {
        return ResponseEntity.ok(storageService.createOrUpdateStorage(storage, cropId));
    }

    @PutMapping("/{id}/uvc")
    public ResponseEntity<FoodStorage> toggleUvc(@PathVariable Long id, @RequestParam Boolean active) {
        return ResponseEntity.ok(storageService.toggleUvc(id, active));
    }

    @GetMapping("/{id}/readings")
    public ResponseEntity<List<FoodStorageReading>> getStorageReadings(@PathVariable Long id) {
        return ResponseEntity.ok(storageService.getStorageReadings(id));
    }
}
