package com.agriloop360.service;

import com.agriloop360.dto.DashboardSummaryDto;
import com.agriloop360.entity.*;
import com.agriloop360.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final WaterReadingRepository waterReadingRepository;
    private final SoilReadingRepository soilReadingRepository;
    private final CompostBatchRepository compostBatchRepository;
    private final FoodStorageRepository foodStorageRepository;
    private final CropRepository cropRepository;
    private final AlertRepository alertRepository;
    private final SensorSimulationService simulationService;

    public DashboardService(WaterReadingRepository waterReadingRepository, SoilReadingRepository soilReadingRepository, CompostBatchRepository compostBatchRepository, FoodStorageRepository foodStorageRepository, CropRepository cropRepository, AlertRepository alertRepository, SensorSimulationService simulationService) {
        this.waterReadingRepository = waterReadingRepository;
        this.soilReadingRepository = soilReadingRepository;
        this.compostBatchRepository = compostBatchRepository;
        this.foodStorageRepository = foodStorageRepository;
        this.cropRepository = cropRepository;
        this.alertRepository = alertRepository;
        this.simulationService = simulationService;
    }

    public DashboardSummaryDto getDashboardSummary() {
        WaterReading latestWater = waterReadingRepository.findTopByOrderByTimestampDesc().orElse(null);
        SoilReading latestSoil = soilReadingRepository.findTopByOrderByTimestampDesc().orElse(null);

        List<CompostBatch> compostBatches = compostBatchRepository.findAllByOrderByUpdatedAtDesc();
        CompostBatch latestCompost = !compostBatches.isEmpty() ? compostBatches.get(0) : null;

        List<FoodStorage> foodStorages = foodStorageRepository.findAllByOrderByTimestampDesc();
        FoodStorage latestStorage = !foodStorages.isEmpty() ? foodStorages.get(0) : null;

        List<Alert> recentAlerts = alertRepository.findTop10ByOrderByTimestampDesc();
        Long activeAlertsCount = alertRepository.countByReadStatusFalse();
        Long activeCropsCount = cropRepository.count();

        String waterStatus = latestWater != null && latestWater.getStatus() != null ? latestWater.getStatus().name() : "GOOD";
        String soilStatus = latestSoil != null ? (latestSoil.getMoisture() > 40 ? "GOOD" : "MODERATE") : "GOOD";
        String fertilizerStatus = latestSoil != null && latestSoil.getStatus() != null ? latestSoil.getStatus() : "OPTIMIZED";
        String compostStatus = latestCompost != null && latestCompost.getStage() != null ? "ACTIVE (" + latestCompost.getStage().name() + ")" : "ACTIVE";
        String cropStatus = activeCropsCount > 0 ? "HEALTHY (" + activeCropsCount + " Crops Active)" : "HEALTHY";
        String storageStatus = latestStorage != null && latestStorage.getSafetyStatus() != null ? latestStorage.getSafetyStatus().name() : "SAFE";

        return DashboardSummaryDto.builder()
                .waterStatus(waterStatus)
                .soilStatus(soilStatus)
                .fertilizerStatus(fertilizerStatus)
                .compostStatus(compostStatus)
                .cropStatus(cropStatus)
                .storageStatus(storageStatus)
                .latestWaterReading(latestWater)
                .latestSoilReading(latestSoil)
                .latestCompostBatch(latestCompost)
                .latestFoodStorage(latestStorage)
                .activeCropsCount(activeCropsCount)
                .activeAlertsCount(activeAlertsCount)
                .recentAlerts(recentAlerts)
                .isSimulationActive(simulationService.isSimulationActive())
                .build();
    }
}
