package com.agriloop360.config;

import com.agriloop360.entity.*;
import com.agriloop360.enumtype.*;
import com.agriloop360.repository.*;
import com.agriloop360.service.RecommendationEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final CropRepository cropRepository;
    private final WaterReadingRepository waterReadingRepository;
    private final SoilReadingRepository soilReadingRepository;
    private final FertilizerRecommendationRepository fertilizerRecRepository;
    private final CompostBatchRepository compostBatchRepository;
    private final CompostReadingRepository compostReadingRepository;
    private final FoodStorageRepository foodStorageRepository;
    private final FoodStorageReadingRepository foodStorageReadingRepository;
    private final AlertRepository alertRepository;
    private final RecommendationEngineService recommendationEngine;

    public DataInitializer(UserRepository userRepository, CropRepository cropRepository, WaterReadingRepository waterReadingRepository, SoilReadingRepository soilReadingRepository, FertilizerRecommendationRepository fertilizerRecRepository, CompostBatchRepository compostBatchRepository, CompostReadingRepository compostReadingRepository, FoodStorageRepository foodStorageRepository, FoodStorageReadingRepository foodStorageReadingRepository, AlertRepository alertRepository, RecommendationEngineService recommendationEngine) {
        this.userRepository = userRepository;
        this.cropRepository = cropRepository;
        this.waterReadingRepository = waterReadingRepository;
        this.soilReadingRepository = soilReadingRepository;
        this.fertilizerRecRepository = fertilizerRecRepository;
        this.compostBatchRepository = compostBatchRepository;
        this.compostReadingRepository = compostReadingRepository;
        this.foodStorageRepository = foodStorageRepository;
        this.foodStorageReadingRepository = foodStorageReadingRepository;
        this.alertRepository = alertRepository;
        this.recommendationEngine = recommendationEngine;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing AGRILOOP 360 sample demo data...");

        if (userRepository.count() == 0) {
            User demoUser = User.builder()
                    .name("Agri Admin")
                    .email("admin@agriloop360.com")
                    .password("password123")
                    .role("ROLE_ADMIN")
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(demoUser);
            log.info("Demo user created: admin@agriloop360.com / password123");
        }

        if (cropRepository.count() == 0) {
            List<Crop> crops = Arrays.asList(
                Crop.builder().name("Brinjal").cropType(CropType.VEGETABLE).soilType("Loamy Soil")
                    .plantingDate(LocalDate.now().minusDays(30)).expectedHarvestDate(LocalDate.now().plusDays(60))
                    .targetPhMin(5.5).targetPhMax(6.8).targetMoistureMin(45.0).targetMoistureMax(65.0)
                    .targetN(90.0).targetP(45.0).targetK(75.0)
                    .targetTempMin(10.0).targetTempMax(13.0).targetHumidityMin(85.0).targetHumidityMax(90.0).build(),

                Crop.builder().name("Green Chilli").cropType(CropType.VEGETABLE).soilType("Sandy Loam")
                    .plantingDate(LocalDate.now().minusDays(25)).expectedHarvestDate(LocalDate.now().plusDays(55))
                    .targetPhMin(6.0).targetPhMax(7.0).targetMoistureMin(40.0).targetMoistureMax(60.0)
                    .targetN(100.0).targetP(50.0).targetK(80.0)
                    .targetTempMin(7.0).targetTempMax(10.0).targetHumidityMin(85.0).targetHumidityMax(90.0).build(),

                Crop.builder().name("Green Beans").cropType(CropType.VEGETABLE).soilType("Silt Loam")
                    .plantingDate(LocalDate.now().minusDays(20)).expectedHarvestDate(LocalDate.now().plusDays(40))
                    .targetPhMin(6.0).targetPhMax(7.5).targetMoistureMin(50.0).targetMoistureMax(70.0)
                    .targetN(60.0).targetP(40.0).targetK(60.0)
                    .targetTempMin(5.0).targetTempMax(8.0).targetHumidityMin(90.0).targetHumidityMax(95.0).build(),

                Crop.builder().name("Tomato").cropType(CropType.FRUIT).soilType("Well-drained Loam")
                    .plantingDate(LocalDate.now().minusDays(40)).expectedHarvestDate(LocalDate.now().plusDays(50))
                    .targetPhMin(6.0).targetPhMax(6.8).targetMoistureMin(50.0).targetMoistureMax(70.0)
                    .targetN(120.0).targetP(60.0).targetK(100.0)
                    .targetTempMin(10.0).targetTempMax(13.0).targetHumidityMin(85.0).targetHumidityMax(90.0).build(),

                Crop.builder().name("Strawberry").cropType(CropType.FRUIT).soilType("Slightly Acidic Loam")
                    .plantingDate(LocalDate.now().minusDays(50)).expectedHarvestDate(LocalDate.now().plusDays(30))
                    .targetPhMin(5.5).targetPhMax(6.5).targetMoistureMin(60.0).targetMoistureMax(75.0)
                    .targetN(80.0).targetP(40.0).targetK(90.0)
                    .targetTempMin(0.0).targetTempMax(2.0).targetHumidityMin(90.0).targetHumidityMax(95.0).build(),

                Crop.builder().name("Apple").cropType(CropType.FRUIT).soilType("Deep Rich Clay Loam")
                    .plantingDate(LocalDate.now().minusDays(180)).expectedHarvestDate(LocalDate.now().plusDays(120))
                    .targetPhMin(6.0).targetPhMax(7.0).targetMoistureMin(45.0).targetMoistureMax(65.0)
                    .targetN(110.0).targetP(50.0).targetK(120.0)
                    .targetTempMin(1.0).targetTempMax(4.0).targetHumidityMin(90.0).targetHumidityMax(95.0).build(),

                Crop.builder().name("Wheat").cropType(CropType.CEREAL).soilType("Clay Loam")
                    .plantingDate(LocalDate.now().minusDays(60)).expectedHarvestDate(LocalDate.now().plusDays(60))
                    .targetPhMin(6.0).targetPhMax(7.5).targetMoistureMin(40.0).targetMoistureMax(60.0)
                    .targetN(140.0).targetP(60.0).targetK(50.0)
                    .targetTempMin(12.0).targetTempMax(18.0).targetHumidityMin(60.0).targetHumidityMax(70.0).build(),

                Crop.builder().name("Rice").cropType(CropType.CEREAL).soilType("Clayey Alluvial")
                    .plantingDate(LocalDate.now().minusDays(45)).expectedHarvestDate(LocalDate.now().plusDays(75))
                    .targetPhMin(5.5).targetPhMax(6.5).targetMoistureMin(70.0).targetMoistureMax(95.0)
                    .targetN(150.0).targetP(60.0).targetK(60.0)
                    .targetTempMin(13.0).targetTempMax(18.0).targetHumidityMin(65.0).targetHumidityMax(80.0).build(),

                Crop.builder().name("Maize").cropType(CropType.CEREAL).soilType("Deep Sandy Loam")
                    .plantingDate(LocalDate.now().minusDays(35)).expectedHarvestDate(LocalDate.now().plusDays(65))
                    .targetPhMin(5.8).targetPhMax(7.0).targetMoistureMin(45.0).targetMoistureMax(65.0)
                    .targetN(130.0).targetP(55.0).targetK(70.0)
                    .targetTempMin(12.0).targetTempMax(16.0).targetHumidityMin(60.0).targetHumidityMax(75.0).build(),

                Crop.builder().name("Barley").cropType(CropType.CEREAL).soilType("Well-drained Loam")
                    .plantingDate(LocalDate.now().minusDays(70)).expectedHarvestDate(LocalDate.now().plusDays(50))
                    .targetPhMin(6.0).targetPhMax(7.5).targetMoistureMin(35.0).targetMoistureMax(55.0)
                    .targetN(90.0).targetP(45.0).targetK(50.0)
                    .targetTempMin(10.0).targetTempMax(15.0).targetHumidityMin(60.0).targetHumidityMax(70.0).build(),

                Crop.builder().name("Oats").cropType(CropType.CEREAL).soilType("Moist Loam")
                    .plantingDate(LocalDate.now().minusDays(55)).expectedHarvestDate(LocalDate.now().plusDays(65))
                    .targetPhMin(5.3).targetPhMax(6.5).targetMoistureMin(40.0).targetMoistureMax(65.0)
                    .targetN(80.0).targetP(40.0).targetK(45.0)
                    .targetTempMin(10.0).targetTempMax(15.0).targetHumidityMin(60.0).targetHumidityMax(70.0).build(),

                Crop.builder().name("Ragi").cropType(CropType.CEREAL).soilType("Red Sandy Loam")
                    .plantingDate(LocalDate.now().minusDays(40)).expectedHarvestDate(LocalDate.now().plusDays(80))
                    .targetPhMin(5.0).targetPhMax(7.0).targetMoistureMin(30.0).targetMoistureMax(50.0)
                    .targetN(70.0).targetP(35.0).targetK(40.0)
                    .targetTempMin(14.0).targetTempMax(20.0).targetHumidityMin(55.0).targetHumidityMax(70.0).build()
            );
            cropRepository.saveAll(crops);
            log.info("12 crops initialized.");
        }

        if (waterReadingRepository.count() == 0) {
            for (int i = 5; i >= 0; i--) {
                WaterReading wr = WaterReading.builder()
                        .ph(7.1 + (i % 2 == 0 ? 0.1 : -0.1))
                        .tds(210.0 + (i * 5))
                        .temperature(25.5 - (i * 0.3))
                        .turbidity("Low")
                        .timestamp(LocalDateTime.now().minusHours(i * 2))
                        .build();
                wr = recommendationEngine.evaluateWaterQuality(wr);
                waterReadingRepository.save(wr);
            }
            log.info("Water readings initialized.");
        }

        if (soilReadingRepository.count() == 0) {
            Crop tomato = cropRepository.findByNameIgnoreCase("Tomato").orElse(null);
            if (tomato != null) {
                SoilReading sr = SoilReading.builder()
                        .crop(tomato)
                        .moisture(54.0)
                        .ph(6.5)
                        .nitrogen(92.0)
                        .phosphorus(55.0)
                        .potassium(95.0)
                        .timestamp(LocalDateTime.now())
                        .build();
                FertilizerRecommendation fr = recommendationEngine.evaluateSoilAndFertilizer(sr, tomato);
                fertilizerRecRepository.save(fr);

                sr.setStatus(fr.getOptimizationStatus().name());
                sr.setRecommendation(fr.getRecommendationText());
                soilReadingRepository.save(sr);
            }
            log.info("Soil & Fertilizer initialized.");
        }

        if (compostBatchRepository.count() == 0) {
            CompostBatch b1 = CompostBatch.builder()
                    .batchName("Batch #A101 - Organic Vegetable Recycler")
                    .wasteType("Vegetable waste & Crop Residue")
                    .quantityKg(250.0)
                    .moisture(55.0)
                    .temperature(52.0)
                    .ph(6.8)
                    .stage(CompostStage.SOLID_PROCESSING)
                    .startDate(LocalDateTime.now().minusDays(12))
                    .updatedAt(LocalDateTime.now())
                    .build();
            b1 = recommendationEngine.evaluateCompost(b1);
            compostBatchRepository.save(b1);

            CompostReading r1 = CompostReading.builder()
                    .compostBatch(b1)
                    .moisture(b1.getMoisture())
                    .temperature(b1.getTemperature())
                    .ph(b1.getPh())
                    .stage(b1.getStage())
                    .statusMsg(b1.getStatusMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
            compostReadingRepository.save(r1);

            CompostBatch b2 = CompostBatch.builder()
                    .batchName("Batch #B204 - Fruit Waste Nutrient Extractor")
                    .wasteType("Fruit waste & Foliage")
                    .quantityKg(180.0)
                    .moisture(62.0)
                    .temperature(48.0)
                    .ph(6.4)
                    .stage(CompostStage.LIQUID_PROCESSING)
                    .startDate(LocalDateTime.now().minusDays(18))
                    .updatedAt(LocalDateTime.now())
                    .build();
            b2 = recommendationEngine.evaluateCompost(b2);
            compostBatchRepository.save(b2);

            log.info("Compost batches initialized.");
        }

        if (foodStorageRepository.count() == 0) {
            Crop tomato = cropRepository.findByNameIgnoreCase("Tomato").orElse(null);
            Crop strawberry = cropRepository.findByNameIgnoreCase("Strawberry").orElse(null);

            if (tomato != null) {
                FoodStorage fs1 = FoodStorage.builder()
                        .storageName("Vault A - Evaporative Cooling Chamber")
                        .crop(tomato)
                        .temperature(11.5)
                        .humidity(87.0)
                        .uvcActive(true)
                        .timestamp(LocalDateTime.now())
                        .build();
                fs1 = recommendationEngine.evaluateFoodStorage(fs1);
                foodStorageRepository.save(fs1);

                FoodStorageReading fsr1 = FoodStorageReading.builder()
                        .foodStorage(fs1)
                        .temperature(fs1.getTemperature())
                        .humidity(fs1.getHumidity())
                        .uvcActive(fs1.getUvcActive())
                        .timestamp(LocalDateTime.now())
                        .build();
                foodStorageReadingRepository.save(fsr1);
            }

            if (strawberry != null) {
                FoodStorage fs2 = FoodStorage.builder()
                        .storageName("Vault B - Cold Sanitized Chamber")
                        .crop(strawberry)
                        .temperature(1.2)
                        .humidity(92.0)
                        .uvcActive(false)
                        .timestamp(LocalDateTime.now())
                        .build();
                fs2 = recommendationEngine.evaluateFoodStorage(fs2);
                foodStorageRepository.save(fs2);
            }
            log.info("Food storage initialized.");
        }

        if (alertRepository.count() == 0) {
            alertRepository.save(Alert.builder()
                    .severity(Severity.INFO)
                    .module(ModuleName.WATER)
                    .message("Digital Water Quality Monitor initialized. Sensor stream ONLINE.")
                    .readStatus(false)
                    .timestamp(LocalDateTime.now().minusMinutes(30))
                    .build());

            alertRepository.save(Alert.builder()
                    .severity(Severity.INFO)
                    .module(ModuleName.COMPOST)
                    .message("Compost Batch #A101 transitioned to Stage 3 (Solid Processing -> Organic Powder).")
                    .readStatus(false)
                    .timestamp(LocalDateTime.now().minusMinutes(15))
                    .build());

            log.info("Initial alerts seeded.");
        }

        log.info("AGRILOOP 360 Sample Data Initialized Successfully.");
    }
}
