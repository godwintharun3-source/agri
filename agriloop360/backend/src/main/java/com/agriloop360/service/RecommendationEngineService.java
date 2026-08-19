package com.agriloop360.service;

import com.agriloop360.entity.*;
import com.agriloop360.enumtype.*;
import com.agriloop360.repository.AlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecommendationEngineService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationEngineService.class);

    private final AlertRepository alertRepository;

    public RecommendationEngineService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public WaterReading evaluateWaterQuality(WaterReading reading) {
        double ph = reading.getPh() != null ? reading.getPh() : 7.0;
        double tds = reading.getTds() != null ? reading.getTds() : 200.0;
        double temp = reading.getTemperature() != null ? reading.getTemperature() : 25.0;
        String turb = reading.getTurbidity() != null ? reading.getTurbidity() : "Low";

        WaterStatus status;
        StringBuilder rec = new StringBuilder();

        boolean phGood = ph >= 6.5 && ph <= 8.5;
        boolean tdsGood = tds >= 100 && tds <= 500;
        boolean tempGood = temp >= 15 && temp <= 30;
        boolean turbGood = "Low".equalsIgnoreCase(turb);

        if (phGood && tdsGood && tempGood && turbGood) {
            status = WaterStatus.EXCELLENT;
            rec.append("Water Quality: EXCELLENT. Suitable for all irrigation systems.");
        } else if (ph >= 6.0 && ph <= 8.8 && tds <= 700 && !"High".equalsIgnoreCase(turb)) {
            status = WaterStatus.GOOD;
            rec.append("Water Quality: GOOD. Suitable for direct crop irrigation.");
        } else if (ph >= 5.5 && ph <= 9.2 && tds <= 1000) {
            status = WaterStatus.MODERATE;
            rec.append("Water Quality: MODERATE. ");
            if ("High".equalsIgnoreCase(turb)) {
                rec.append("Filtration/settling recommended due to turbidity. ");
            }
            if (tds > 700) {
                rec.append("Dilution recommended for sensitive crops. ");
            }
        } else {
            status = WaterStatus.POOR;
            rec.append("WARNING: Water quality requires treatment before irrigation! ");
            if (!phGood) rec.append("pH (").append(ph).append(") is outside safe range (6.5-8.5). ");
            if (tds > 1000) rec.append("TDS (").append(tds).append(" ppm) is high. ");
            if ("High".equalsIgnoreCase(turb)) rec.append("High turbidity detected - heavy settling required. ");
            rec.append("Avoid irrigation until treated.");
        }

        reading.setStatus(status);
        reading.setRecommendation(rec.toString());

        if (status == WaterStatus.POOR) {
            createAlert(Severity.CRITICAL, ModuleName.WATER, "Critical Water Quality Alert: " + rec.toString());
        } else if (status == WaterStatus.MODERATE) {
            createAlert(Severity.WARNING, ModuleName.WATER, "Water Quality Warning: " + rec.toString());
        }

        return reading;
    }

    public FertilizerRecommendation evaluateSoilAndFertilizer(SoilReading reading, Crop crop) {
        if (crop == null) {
            throw new IllegalArgumentException("Crop must be specified for fertilizer evaluation");
        }

        double n = reading.getNitrogen() != null ? reading.getNitrogen() : 50.0;
        double p = reading.getPhosphorus() != null ? reading.getPhosphorus() : 30.0;
        double k = reading.getPotassium() != null ? reading.getPotassium() : 40.0;
        double moisture = reading.getMoisture() != null ? reading.getMoisture() : 50.0;
        double ph = reading.getPh() != null ? reading.getPh() : 6.5;

        double targetN = crop.getTargetN() != null ? crop.getTargetN() : 100.0;
        double targetP = crop.getTargetP() != null ? crop.getTargetP() : 40.0;
        double targetK = crop.getTargetK() != null ? crop.getTargetK() : 80.0;
        double targetMoistureMin = crop.getTargetMoistureMin() != null ? crop.getTargetMoistureMin() : 40.0;
        double targetMoistureMax = crop.getTargetMoistureMax() != null ? crop.getTargetMoistureMax() : 70.0;
        double targetPhMin = crop.getTargetPhMin() != null ? crop.getTargetPhMin() : 6.0;
        double targetPhMax = crop.getTargetPhMax() != null ? crop.getTargetPhMax() : 7.5;

        String nStatus = n < targetN * 0.85 ? "Low" : (n > targetN * 1.15 ? "High" : "Optimal");
        String pStatus = p < targetP * 0.85 ? "Low" : (p > targetP * 1.15 ? "High" : "Optimal");
        String kStatus = k < targetK * 0.85 ? "Low" : (k > targetK * 1.15 ? "High" : "Optimal");
        String moistureStatus = moisture < targetMoistureMin ? "Low" : (moisture > targetMoistureMax ? "High" : "Optimal");
        String phStatus = ph < targetPhMin ? "Acidic" : (ph > targetPhMax ? "Alkaline" : "Optimal");

        OptimizationStatus optStatus;
        StringBuilder recText = new StringBuilder();

        boolean allOptimal = "Optimal".equals(nStatus) && "Optimal".equals(pStatus) && 
                             "Optimal".equals(kStatus) && "Optimal".equals(moistureStatus) && 
                             "Optimal".equals(phStatus);

        if (allOptimal) {
            optStatus = OptimizationStatus.OPTIMIZED;
            recText.append("Soil parameters are perfectly optimized for ").append(crop.getName()).append(". Maintain current management.");
        } else {
            boolean hasDeficiency = "Low".equals(nStatus) || "Low".equals(pStatus) || "Low".equals(kStatus);
            boolean hasExcess = "High".equals(nStatus) || "High".equals(pStatus) || "High".equals(kStatus);

            if (hasDeficiency) {
                optStatus = OptimizationStatus.DEFICIENT;
            } else if (hasExcess) {
                optStatus = OptimizationStatus.EXCESS_APPLICATION;
            } else {
                optStatus = OptimizationStatus.NEEDS_ADJUSTMENT;
            }

            recText.append("Recommendation for ").append(crop.getName()).append(": ");
            if ("Low".equals(nStatus)) {
                recText.append("Nitrogen is below required level (Current: ").append(n).append(" ppm, Target: ").append(targetN).append(" ppm). Apply organic compost or nitrogen source. ");
            } else if ("High".equals(nStatus)) {
                recText.append("Nitrogen is high (").append(n).append(" ppm). Avoid excessive nitrogen application. ");
            }

            if ("Low".equals(pStatus)) {
                recText.append("Phosphorus is deficient (Current: ").append(p).append(" ppm, Target: ").append(targetP).append(" ppm). Apply rock phosphate or bone meal. ");
            }

            if ("Low".equals(kStatus)) {
                recText.append("Potassium is low (Current: ").append(k).append(" ppm, Target: ").append(targetK).append(" ppm). Apply potash or organic wood ash. ");
            }

            if ("Low".equals(moistureStatus)) {
                recText.append("Soil moisture is LOW (").append(moisture).append("%). Immediate irrigation recommended. ");
            } else if ("High".equals(moistureStatus)) {
                recText.append("Soil moisture is HIGH (").append(moisture).append("%). Ensure adequate drainage to prevent root rot. ");
            }

            if ("Acidic".equals(phStatus)) {
                recText.append("Soil pH is acidic (").append(ph).append("). Consider agricultural lime application. ");
            } else if ("Alkaline".equals(phStatus)) {
                recText.append("Soil pH is alkaline (").append(ph).append("). Consider elemental sulfur treatment. ");
            }
        }

        FertilizerRecommendation rec = FertilizerRecommendation.builder()
                .crop(crop)
                .nStatus(nStatus)
                .pStatus(pStatus)
                .kStatus(kStatus)
                .moistureStatus(moistureStatus)
                .phStatus(phStatus)
                .recommendationText(recText.toString())
                .optimizationStatus(optStatus)
                .timestamp(LocalDateTime.now())
                .build();

        if (optStatus == OptimizationStatus.DEFICIENT || "Low".equals(moistureStatus)) {
            createAlert(Severity.WARNING, ModuleName.SOIL, "Soil Alert for " + crop.getName() + ": " + recText.toString());
        }

        return rec;
    }

    public CompostBatch evaluateCompost(CompostBatch batch) {
        double moisture = batch.getMoisture() != null ? batch.getMoisture() : 50.0;
        double temp = batch.getTemperature() != null ? batch.getTemperature() : 45.0;
        double ph = batch.getPh() != null ? batch.getPh() : 7.0;
        double qty = batch.getQuantityKg() != null ? batch.getQuantityKg() : 100.0;

        StringBuilder msg = new StringBuilder();
        StringBuilder rec = new StringBuilder();

        if (batch.getStage() == CompostStage.SOLID_PROCESSING || batch.getStage() == CompostStage.AGRICULTURAL_APPLICATION) {
            batch.setOrganicPowderOutputKg(Math.round(qty * 0.38 * 10.0) / 10.0);
        } else {
            batch.setOrganicPowderOutputKg(0.0);
        }

        if (batch.getStage() == CompostStage.LIQUID_PROCESSING || batch.getStage() == CompostStage.AGRICULTURAL_APPLICATION) {
            batch.setNutrientSolutionOutputLiters(Math.round(qty * 0.28 * 10.0) / 10.0);
        } else {
            batch.setNutrientSolutionOutputLiters(0.0);
        }

        if (moisture > 65) {
            msg.append("Excess Moisture. ");
            rec.append("Compost Moisture HIGH (").append(moisture).append("%). Adjust moisture and add carbon-rich material (dry leaves, straw). ");
        } else if (moisture < 40) {
            msg.append("Low Moisture. ");
            rec.append("Compost Moisture LOW (").append(moisture).append("%). Add water or liquid nutrient runoff to maintain decomposition. ");
        } else {
            msg.append("Optimal Moisture. ");
        }

        if (temp >= 45 && temp <= 65) {
            msg.append("Active Thermophilic Decomposition. ");
        } else if (temp < 40) {
            msg.append("Low Temperature / Slow Microbial Activity. ");
            rec.append("Turn compost pile to increase oxygenation and microbial activity. ");
        } else if (temp > 70) {
            msg.append("Overheating Risk. ");
            rec.append("Turn pile immediately to prevent overheating and beneficial microbe loss. ");
        }

        if (ph < 6.0 || ph > 8.5) {
            msg.append("Nutrient/pH Imbalance. ");
        }

        batch.setStatusMessage(msg.toString());
        batch.setRecommendation(rec.length() > 0 ? rec.toString() : "Composting process is running normally. Ready for next stage processing.");
        batch.setUpdatedAt(LocalDateTime.now());

        if (moisture > 65 || temp > 70) {
            createAlert(Severity.WARNING, ModuleName.COMPOST, "Compost Warning (" + batch.getBatchName() + "): " + batch.getRecommendation());
        }

        return batch;
    }

    public FoodStorage evaluateFoodStorage(FoodStorage storage) {
        Crop crop = storage.getCrop();
        double temp = storage.getTemperature() != null ? storage.getTemperature() : 10.0;
        double hum = storage.getHumidity() != null ? storage.getHumidity() : 85.0;
        boolean uvc = Boolean.TRUE.equals(storage.getUvcActive());

        double minTemp = crop != null && crop.getTargetTempMin() != null ? crop.getTargetTempMin() : 5.0;
        double maxTemp = crop != null && crop.getTargetTempMax() != null ? crop.getTargetTempMax() : 15.0;
        double minHum = crop != null && crop.getTargetHumidityMin() != null ? crop.getTargetHumidityMin() : 80.0;
        double maxHum = crop != null && crop.getTargetHumidityMax() != null ? crop.getTargetHumidityMax() : 95.0;

        StorageSafetyStatus status = StorageSafetyStatus.SAFE;
        StringBuilder warning = new StringBuilder();

        String cropName = crop != null ? crop.getName() : "Produce";

        if (temp > maxTemp) {
            status = StorageSafetyStatus.WARNING_SPOILAGE_RISK;
            warning.append("High Temperature (").append(temp).append("°C) - Rapid spoilage & microbial growth risk for ").append(cropName).append("! (Max safe: ").append(maxTemp).append("°C). ");
        } else if (temp < minTemp) {
            status = StorageSafetyStatus.WARNING_CHILLING_RISK;
            warning.append("Low Temperature (").append(temp).append("°C) - Chilling injury risk for ").append(cropName).append("! (Min safe: ").append(minTemp).append("°C). ");
        }

        if (hum < minHum || hum > maxHum) {
            if (status == StorageSafetyStatus.SAFE) status = StorageSafetyStatus.WARNING_HUMIDITY_OUT_OF_RANGE;
            warning.append("Humidity (").append(hum).append("%) outside configured range (").append(minHum).append("-").append(maxHum).append("%). ");
        }

        if (uvc) {
            warning.append("UV-C mild sanitization treatment ACTIVE. Microbial load reducing.");
        }

        storage.setSafetyStatus(status);
        storage.setWarningMessage(warning.length() > 0 ? warning.toString() : "Storage conditions optimal for " + cropName + " preservation.");
        storage.setTimestamp(LocalDateTime.now());

        if (status == StorageSafetyStatus.WARNING_SPOILAGE_RISK || status == StorageSafetyStatus.WARNING_CHILLING_RISK) {
            createAlert(Severity.CRITICAL, ModuleName.STORAGE, "Food Storage Critical Alert (" + storage.getStorageName() + "): " + warning.toString());
        } else if (status != StorageSafetyStatus.SAFE) {
            createAlert(Severity.WARNING, ModuleName.STORAGE, "Food Storage Warning (" + storage.getStorageName() + "): " + warning.toString());
        }

        return storage;
    }

    private void createAlert(Severity severity, ModuleName module, String message) {
        try {
            Alert alert = Alert.builder()
                    .severity(severity)
                    .module(module)
                    .message(message)
                    .readStatus(false)
                    .timestamp(LocalDateTime.now())
                    .build();
            alertRepository.save(alert);
        } catch (Exception e) {
            log.error("Error creating alert: {}", e.getMessage());
        }
    }
}
