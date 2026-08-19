package com.agriloop360.service;

import com.agriloop360.entity.Crop;
import com.agriloop360.entity.FertilizerRecommendation;
import com.agriloop360.entity.SoilReading;
import com.agriloop360.exception.ResourceNotFoundException;
import com.agriloop360.repository.CropRepository;
import com.agriloop360.repository.FertilizerRecommendationRepository;
import com.agriloop360.repository.SoilReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FertilizerService {

    private final FertilizerRecommendationRepository recommendationRepository;
    private final SoilReadingRepository soilReadingRepository;
    private final CropRepository cropRepository;
    private final RecommendationEngineService recommendationEngine;

    public FertilizerService(FertilizerRecommendationRepository recommendationRepository, SoilReadingRepository soilReadingRepository, CropRepository cropRepository, RecommendationEngineService recommendationEngine) {
        this.recommendationRepository = recommendationRepository;
        this.soilReadingRepository = soilReadingRepository;
        this.cropRepository = cropRepository;
        this.recommendationEngine = recommendationEngine;
    }

    public FertilizerRecommendation generateRecommendation(Long cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + cropId));

        SoilReading latestSoil = soilReadingRepository.findTopByCropIdOrderByTimestampDesc(cropId)
                .orElseGet(() -> SoilReading.builder()
                        .crop(crop)
                        .moisture(50.0)
                        .ph(6.5)
                        .nitrogen(crop.getTargetN() != null ? crop.getTargetN() * 0.8 : 80.0)
                        .phosphorus(crop.getTargetP() != null ? crop.getTargetP() : 35.0)
                        .potassium(crop.getTargetK() != null ? crop.getTargetK() : 70.0)
                        .build());

        FertilizerRecommendation rec = recommendationEngine.evaluateSoilAndFertilizer(latestSoil, crop);
        return recommendationRepository.save(rec);
    }

    public FertilizerRecommendation getLatestByCrop(Long cropId) {
        return recommendationRepository.findTopByCropIdOrderByTimestampDesc(cropId)
                .orElseGet(() -> generateRecommendation(cropId));
    }

    public List<FertilizerRecommendation> getAllRecent() {
        return recommendationRepository.findTop10ByOrderByTimestampDesc();
    }
}
