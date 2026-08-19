package com.agriloop360.service;

import com.agriloop360.entity.WaterReading;
import com.agriloop360.repository.WaterReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WaterService {

    private final WaterReadingRepository waterReadingRepository;
    private final RecommendationEngineService recommendationEngine;

    public WaterService(WaterReadingRepository waterReadingRepository, RecommendationEngineService recommendationEngine) {
        this.waterReadingRepository = waterReadingRepository;
        this.recommendationEngine = recommendationEngine;
    }

    public WaterReading getLatestReading() {
        return waterReadingRepository.findTopByOrderByTimestampDesc()
                .orElseGet(() -> {
                    WaterReading r = WaterReading.builder()
                            .ph(7.2)
                            .tds(210.0)
                            .temperature(25.0)
                            .turbidity("Low")
                            .timestamp(LocalDateTime.now())
                            .build();
                    return recommendationEngine.evaluateWaterQuality(r);
                });
    }

    public List<WaterReading> getHistoricalReadings() {
        return waterReadingRepository.findTop20ByOrderByTimestampDesc();
    }

    public WaterReading saveReading(WaterReading reading) {
        if (reading.getTimestamp() == null) {
            reading.setTimestamp(LocalDateTime.now());
        }
        WaterReading evaluated = recommendationEngine.evaluateWaterQuality(reading);
        return waterReadingRepository.save(evaluated);
    }
}
