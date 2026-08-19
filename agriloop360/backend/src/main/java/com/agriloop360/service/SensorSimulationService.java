package com.agriloop360.service;

import com.agriloop360.entity.*;
import com.agriloop360.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@EnableScheduling
public class SensorSimulationService {

    private static final Logger log = LoggerFactory.getLogger(SensorSimulationService.class);

    private final WaterReadingRepository waterReadingRepository;
    private final SoilReadingRepository soilReadingRepository;
    private final CompostBatchRepository compostBatchRepository;
    private final CompostReadingRepository compostReadingRepository;
    private final FoodStorageRepository foodStorageRepository;
    private final FoodStorageReadingRepository foodStorageReadingRepository;
    private final CropRepository cropRepository;
    private final RecommendationEngineService recommendationEngine;

    private boolean simulationActive = true;
    private final Random random = new Random();

    public SensorSimulationService(WaterReadingRepository waterReadingRepository, SoilReadingRepository soilReadingRepository, CompostBatchRepository compostBatchRepository, CompostReadingRepository compostReadingRepository, FoodStorageRepository foodStorageRepository, FoodStorageReadingRepository foodStorageReadingRepository, CropRepository cropRepository, RecommendationEngineService recommendationEngine) {
        this.waterReadingRepository = waterReadingRepository;
        this.soilReadingRepository = soilReadingRepository;
        this.compostBatchRepository = compostBatchRepository;
        this.compostReadingRepository = compostReadingRepository;
        this.foodStorageRepository = foodStorageRepository;
        this.foodStorageReadingRepository = foodStorageReadingRepository;
        this.cropRepository = cropRepository;
        this.recommendationEngine = recommendationEngine;
    }

    public boolean isSimulationActive() {
        return simulationActive;
    }

    public void startSimulation() {
        this.simulationActive = true;
        log.info("Sensor simulation started");
    }

    public void stopSimulation() {
        this.simulationActive = false;
        log.info("Sensor simulation stopped");
    }

    @Scheduled(fixedRate = 10000)
    public void scheduledTick() {
        if (simulationActive) {
            triggerTick();
        }
    }

    @Transactional
    public void triggerTick() {
        log.info("Generating simulated IoT sensor tick across all 4 modules...");

        simulateWaterReading();
        simulateSoilReading();
        simulateCompostReading();
        simulateFoodStorageReading();
    }

    private void simulateWaterReading() {
        WaterReading latest = waterReadingRepository.findTopByOrderByTimestampDesc()
                .orElse(WaterReading.builder().ph(7.2).tds(210.0).temperature(24.0).turbidity("Low").build());

        double ph = Math.round((latest.getPh() + (random.nextDouble() * 0.4 - 0.2)) * 100.0) / 100.0;
        ph = Math.max(5.0, Math.min(9.5, ph));

        double tds = Math.round((latest.getTds() + (random.nextDouble() * 20.0 - 10.0)) * 10.0) / 10.0;
        tds = Math.max(50.0, Math.min(1200.0, tds));

        double temp = Math.round((latest.getTemperature() + (random.nextDouble() * 1.0 - 0.5)) * 10.0) / 10.0;
        temp = Math.max(12.0, Math.min(38.0, temp));

        String[] turbLevels = {"Low", "Low", "Low", "Medium", "High"};
        String turb = turbLevels[random.nextInt(turbLevels.length)];

        WaterReading newReading = WaterReading.builder()
                .ph(ph)
                .tds(tds)
                .temperature(temp)
                .turbidity(turb)
                .timestamp(LocalDateTime.now())
                .build();

        newReading = recommendationEngine.evaluateWaterQuality(newReading);
        waterReadingRepository.save(newReading);
    }

    private void simulateSoilReading() {
        List<Crop> crops = cropRepository.findAll();
        if (crops.isEmpty()) return;

        Crop crop = crops.get(random.nextInt(crops.size()));

        SoilReading latest = soilReadingRepository.findTopByCropIdOrderByTimestampDesc(crop.getId())
                .orElse(SoilReading.builder()
                        .moisture(55.0)
                        .ph(6.5)
                        .nitrogen(crop.getTargetN() != null ? crop.getTargetN() : 100.0)
                        .phosphorus(crop.getTargetP() != null ? crop.getTargetP() : 40.0)
                        .potassium(crop.getTargetK() != null ? crop.getTargetK() : 80.0)
                        .build());

        double moisture = Math.round((latest.getMoisture() + (random.nextDouble() * 6.0 - 3.0)) * 10.0) / 10.0;
        moisture = Math.max(15.0, Math.min(90.0, moisture));

        double ph = Math.round((latest.getPh() + (random.nextDouble() * 0.2 - 0.1)) * 100.0) / 100.0;
        ph = Math.max(4.5, Math.min(9.0, ph));

        double n = Math.round((latest.getNitrogen() + (random.nextDouble() * 10.0 - 5.0)) * 10.0) / 10.0;
        double p = Math.round((latest.getPhosphorus() + (random.nextDouble() * 4.0 - 2.0)) * 10.0) / 10.0;
        double k = Math.round((latest.getPotassium() + (random.nextDouble() * 6.0 - 3.0)) * 10.0) / 10.0;

        SoilReading newReading = SoilReading.builder()
                .crop(crop)
                .moisture(moisture)
                .ph(ph)
                .nitrogen(Math.max(10.0, n))
                .phosphorus(Math.max(5.0, p))
                .potassium(Math.max(10.0, k))
                .timestamp(LocalDateTime.now())
                .build();

        FertilizerRecommendation rec = recommendationEngine.evaluateSoilAndFertilizer(newReading, crop);
        newReading.setStatus(rec.getOptimizationStatus().name());
        newReading.setRecommendation(rec.getRecommendationText());

        soilReadingRepository.save(newReading);
    }

    private void simulateCompostReading() {
        List<CompostBatch> batches = compostBatchRepository.findAll();
        if (batches.isEmpty()) return;

        for (CompostBatch batch : batches) {
            double moisture = Math.round((batch.getMoisture() + (random.nextDouble() * 4.0 - 2.0)) * 10.0) / 10.0;
            moisture = Math.max(25.0, Math.min(85.0, moisture));

            double temp = Math.round((batch.getTemperature() + (random.nextDouble() * 3.0 - 1.5)) * 10.0) / 10.0;
            temp = Math.max(20.0, Math.min(75.0, temp));

            double ph = Math.round((batch.getPh() + (random.nextDouble() * 0.2 - 0.1)) * 100.0) / 100.0;
            ph = Math.max(5.0, Math.min(9.0, ph));

            batch.setMoisture(moisture);
            batch.setTemperature(temp);
            batch.setPh(ph);

            recommendationEngine.evaluateCompost(batch);
            compostBatchRepository.save(batch);

            CompostReading reading = CompostReading.builder()
                    .compostBatch(batch)
                    .moisture(moisture)
                    .temperature(temp)
                    .ph(ph)
                    .stage(batch.getStage())
                    .statusMsg(batch.getStatusMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
            compostReadingRepository.save(reading);
        }
    }

    private void simulateFoodStorageReading() {
        List<FoodStorage> storages = foodStorageRepository.findAll();
        if (storages.isEmpty()) return;

        for (FoodStorage storage : storages) {
            double temp = Math.round((storage.getTemperature() + (random.nextDouble() * 1.5 - 0.75)) * 10.0) / 10.0;
            double hum = Math.round((storage.getHumidity() + (random.nextDouble() * 2.0 - 1.0)) * 10.0) / 10.0;

            storage.setTemperature(temp);
            storage.setHumidity(hum);

            recommendationEngine.evaluateFoodStorage(storage);
            foodStorageRepository.save(storage);

            FoodStorageReading reading = FoodStorageReading.builder()
                    .foodStorage(storage)
                    .temperature(temp)
                    .humidity(hum)
                    .uvcActive(storage.getUvcActive())
                    .timestamp(LocalDateTime.now())
                    .build();
            foodStorageReadingRepository.save(reading);
        }
    }
}
