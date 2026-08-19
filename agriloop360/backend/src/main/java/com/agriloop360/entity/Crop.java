package com.agriloop360.entity;

import com.agriloop360.enumtype.CropType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crops")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropType cropType;

    private String soilType;

    private LocalDate plantingDate;

    private LocalDate expectedHarvestDate;

    private Double targetPhMin;
    private Double targetPhMax;
    private Double targetMoistureMin;
    private Double targetMoistureMax;

    private Double targetN;
    private Double targetP;
    private Double targetK;

    private Double targetTempMin;
    private Double targetTempMax;
    private Double targetHumidityMin;
    private Double targetHumidityMax;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Crop() {}

    public Crop(Long id, String name, CropType cropType, String soilType, LocalDate plantingDate, LocalDate expectedHarvestDate, Double targetPhMin, Double targetPhMax, Double targetMoistureMin, Double targetMoistureMax, Double targetN, Double targetP, Double targetK, Double targetTempMin, Double targetTempMax, Double targetHumidityMin, Double targetHumidityMax, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.cropType = cropType;
        this.soilType = soilType;
        this.plantingDate = plantingDate;
        this.expectedHarvestDate = expectedHarvestDate;
        this.targetPhMin = targetPhMin;
        this.targetPhMax = targetPhMax;
        this.targetMoistureMin = targetMoistureMin;
        this.targetMoistureMax = targetMoistureMax;
        this.targetN = targetN;
        this.targetP = targetP;
        this.targetK = targetK;
        this.targetTempMin = targetTempMin;
        this.targetTempMax = targetTempMax;
        this.targetHumidityMin = targetHumidityMin;
        this.targetHumidityMax = targetHumidityMax;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public static CropBuilder builder() {
        return new CropBuilder();
    }

    public static class CropBuilder {
        private Long id;
        private String name;
        private CropType cropType;
        private String soilType;
        private LocalDate plantingDate;
        private LocalDate expectedHarvestDate;
        private Double targetPhMin;
        private Double targetPhMax;
        private Double targetMoistureMin;
        private Double targetMoistureMax;
        private Double targetN;
        private Double targetP;
        private Double targetK;
        private Double targetTempMin;
        private Double targetTempMax;
        private Double targetHumidityMin;
        private Double targetHumidityMax;
        private LocalDateTime createdAt = LocalDateTime.now();

        public CropBuilder id(Long id) { this.id = id; return this; }
        public CropBuilder name(String name) { this.name = name; return this; }
        public CropBuilder cropType(CropType cropType) { this.cropType = cropType; return this; }
        public CropBuilder soilType(String soilType) { this.soilType = soilType; return this; }
        public CropBuilder plantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; return this; }
        public CropBuilder expectedHarvestDate(LocalDate expectedHarvestDate) { this.expectedHarvestDate = expectedHarvestDate; return this; }
        public CropBuilder targetPhMin(Double targetPhMin) { this.targetPhMin = targetPhMin; return this; }
        public CropBuilder targetPhMax(Double targetPhMax) { this.targetPhMax = targetPhMax; return this; }
        public CropBuilder targetMoistureMin(Double targetMoistureMin) { this.targetMoistureMin = targetMoistureMin; return this; }
        public CropBuilder targetMoistureMax(Double targetMoistureMax) { this.targetMoistureMax = targetMoistureMax; return this; }
        public CropBuilder targetN(Double targetN) { this.targetN = targetN; return this; }
        public CropBuilder targetP(Double targetP) { this.targetP = targetP; return this; }
        public CropBuilder targetK(Double targetK) { this.targetK = targetK; return this; }
        public CropBuilder targetTempMin(Double targetTempMin) { this.targetTempMin = targetTempMin; return this; }
        public CropBuilder targetTempMax(Double targetTempMax) { this.targetTempMax = targetTempMax; return this; }
        public CropBuilder targetHumidityMin(Double targetHumidityMin) { this.targetHumidityMin = targetHumidityMin; return this; }
        public CropBuilder targetHumidityMax(Double targetHumidityMax) { this.targetHumidityMax = targetHumidityMax; return this; }
        public CropBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Crop build() {
            return new Crop(id, name, cropType, soilType, plantingDate, expectedHarvestDate, targetPhMin, targetPhMax, targetMoistureMin, targetMoistureMax, targetN, targetP, targetK, targetTempMin, targetTempMax, targetHumidityMin, targetHumidityMax, createdAt);
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CropType getCropType() { return cropType; }
    public void setCropType(CropType cropType) { this.cropType = cropType; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public LocalDate getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; }
    public LocalDate getExpectedHarvestDate() { return expectedHarvestDate; }
    public void setExpectedHarvestDate(LocalDate expectedHarvestDate) { this.expectedHarvestDate = expectedHarvestDate; }
    public Double getTargetPhMin() { return targetPhMin; }
    public void setTargetPhMin(Double targetPhMin) { this.targetPhMin = targetPhMin; }
    public Double getTargetPhMax() { return targetPhMax; }
    public void setTargetPhMax(Double targetPhMax) { this.targetPhMax = targetPhMax; }
    public Double getTargetMoistureMin() { return targetMoistureMin; }
    public void setTargetMoistureMin(Double targetMoistureMin) { this.targetMoistureMin = targetMoistureMin; }
    public Double getTargetMoistureMax() { return targetMoistureMax; }
    public void setTargetMoistureMax(Double targetMoistureMax) { this.targetMoistureMax = targetMoistureMax; }
    public Double getTargetN() { return targetN; }
    public void setTargetN(Double targetN) { this.targetN = targetN; }
    public Double getTargetP() { return targetP; }
    public void setTargetP(Double targetP) { this.targetP = targetP; }
    public Double getTargetK() { return targetK; }
    public void setTargetK(Double targetK) { this.targetK = targetK; }
    public Double getTargetTempMin() { return targetTempMin; }
    public void setTargetTempMin(Double targetTempMin) { this.targetTempMin = targetTempMin; }
    public Double getTargetTempMax() { return targetTempMax; }
    public void setTargetTempMax(Double targetTempMax) { this.targetTempMax = targetTempMax; }
    public Double getTargetHumidityMin() { return targetHumidityMin; }
    public void setTargetHumidityMin(Double targetHumidityMin) { this.targetHumidityMin = targetHumidityMin; }
    public Double getTargetHumidityMax() { return targetHumidityMax; }
    public void setTargetHumidityMax(Double targetHumidityMax) { this.targetHumidityMax = targetHumidityMax; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
