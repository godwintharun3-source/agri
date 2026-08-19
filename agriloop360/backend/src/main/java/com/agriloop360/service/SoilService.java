package com.agriloop360.service;

import com.agriloop360.entity.Crop;
import com.agriloop360.entity.FertilizerRecommendation;
import com.agriloop360.entity.SoilReading;
import com.agriloop360.exception.ResourceNotFoundException;
import com.agriloop360.repository.CropRepository;
import com.agriloop360.repository.FertilizerRecommendationRepository;
import com.agriloop360.repository.SoilReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SoilService {

    private final SoilReadingRepository soilReadingRepository;
    private final CropRepository cropRepository;
    private final FertilizerRecommendationRepository fertilizerRecRepository;
    private final RecommendationEngineService recommendationEngine;

    public SoilService(SoilReadingRepository soilReadingRepository, CropRepository cropRepository, FertilizerRecommendationRepository fertilizerRecRepository, RecommendationEngineService recommendationEngine) {
        this.soilReadingRepository = soilReadingRepository;
        this.cropRepository = cropRepository;
        this.fertilizerRecRepository = fertilizerRecRepository;
        this.recommendationEngine = recommendationEngine;
    }

    public SoilReading getLatestReading(Long cropId) {
        if (cropId != null) {
            return soilReadingRepository.findTopByCropIdOrderByTimestampDesc(cropId)
                    .orElseGet(() -> createDefaultSoilReading(cropId));
        }
        return soilReadingRepository.findTopByOrderByTimestampDesc()
                .orElseGet(() -> createDefaultSoilReading(null));
    }

    public List<SoilReading> getHistoricalReadings(Long cropId) {
        if (cropId != null) {
            return soilReadingRepository.findTop20ByCropIdOrderByTimestampDesc(cropId);
        }
        return soilReadingRepository.findTop20ByOrderByTimestampDesc();
    }

    public SoilReading recordSoilReading(SoilReading reading, Long cropId) {
        Crop crop = cropRepository.findById(cropId != null ? cropId : (reading.getCrop() != null ? reading.getCrop().getId() : 1L))
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + cropId));

        reading.setCrop(crop);
        if (reading.getTimestamp() == null) {
            reading.setTimestamp(LocalDateTime.now());
        }

        FertilizerRecommendation rec = recommendationEngine.evaluateSoilAndFertilizer(reading, crop);
        fertilizerRecRepository.save(rec);

        reading.setStatus(rec.getOptimizationStatus().name());
        reading.setRecommendation(rec.getRecommendationText());

        return soilReadingRepository.save(reading);
    }

    private SoilReading createDefaultSoilReading(Long cropId) {
        Crop crop = null;
        if (cropId != null) {
            crop = cropRepository.findById(cropId).orElse(null);
        }
        if (crop == null) {
            List<Crop> crops = cropRepository.findAll();
            if (!crops.isEmpty()) crop = crops.get(0);
        }

        return SoilReading.builder()
                .crop(crop)
                .moisture(52.0)
                .ph(6.5)
                .nitrogen(85.0)
                .phosphorus(35.0)
                .potassium(75.0)
                .status("OPTIMIZED")
                .recommendation("Soil parameters within acceptable ranges.")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
