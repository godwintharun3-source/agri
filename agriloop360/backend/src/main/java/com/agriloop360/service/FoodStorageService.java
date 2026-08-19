package com.agriloop360.service;

import com.agriloop360.entity.Crop;
import com.agriloop360.entity.FoodStorage;
import com.agriloop360.entity.FoodStorageReading;
import com.agriloop360.exception.ResourceNotFoundException;
import com.agriloop360.repository.CropRepository;
import com.agriloop360.repository.FoodStorageReadingRepository;
import com.agriloop360.repository.FoodStorageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoodStorageService {

    private final FoodStorageRepository storageRepository;
    private final FoodStorageReadingRepository readingRepository;
    private final CropRepository cropRepository;
    private final RecommendationEngineService recommendationEngine;

    public FoodStorageService(FoodStorageRepository storageRepository, FoodStorageReadingRepository readingRepository, CropRepository cropRepository, RecommendationEngineService recommendationEngine) {
        this.storageRepository = storageRepository;
        this.readingRepository = readingRepository;
        this.cropRepository = cropRepository;
        this.recommendationEngine = recommendationEngine;
    }

    public List<FoodStorage> getAllStorageUnits() {
        return storageRepository.findAllByOrderByTimestampDesc();
    }

    public FoodStorage getStorageById(Long id) {
        return storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food storage unit not found with id: " + id));
    }

    public FoodStorage createOrUpdateStorage(FoodStorage storage, Long cropId) {
        if (cropId != null) {
            Crop crop = cropRepository.findById(cropId)
                    .orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + cropId));
            storage.setCrop(crop);
        }

        storage.setTimestamp(LocalDateTime.now());
        FoodStorage evaluated = recommendationEngine.evaluateFoodStorage(storage);
        FoodStorage saved = storageRepository.save(evaluated);

        FoodStorageReading reading = FoodStorageReading.builder()
                .foodStorage(saved)
                .temperature(saved.getTemperature())
                .humidity(saved.getHumidity())
                .uvcActive(saved.getUvcActive())
                .timestamp(LocalDateTime.now())
                .build();
        readingRepository.save(reading);

        return saved;
    }

    public FoodStorage toggleUvc(Long id, Boolean uvcActive) {
        FoodStorage storage = getStorageById(id);
        storage.setUvcActive(uvcActive);
        return createOrUpdateStorage(storage, storage.getCrop() != null ? storage.getCrop().getId() : null);
    }

    public List<FoodStorageReading> getStorageReadings(Long storageId) {
        return readingRepository.findTop20ByFoodStorageIdOrderByTimestampDesc(storageId);
    }
}
