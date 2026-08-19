package com.agriloop360.entity;

import com.agriloop360.enumtype.CompostStage;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compost_batches")
public class CompostBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String batchName;

    private String wasteType;
    private Double quantityKg;
    private Double moisture;
    private Double temperature;
    private Double ph;

    @Enumerated(EnumType.STRING)
    private CompostStage stage;

    private String statusMessage;

    @Column(length = 1000)
    private String recommendation;

    private Double organicPowderOutputKg;
    private Double nutrientSolutionOutputLiters;

    private LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public CompostBatch() {}

    public CompostBatch(Long id, String batchName, String wasteType, Double quantityKg, Double moisture, Double temperature, Double ph, CompostStage stage, String statusMessage, String recommendation, Double organicPowderOutputKg, Double nutrientSolutionOutputLiters, LocalDateTime startDate, LocalDateTime updatedAt) {
        this.id = id;
        this.batchName = batchName;
        this.wasteType = wasteType;
        this.quantityKg = quantityKg;
        this.moisture = moisture;
        this.temperature = temperature;
        this.ph = ph;
        this.stage = stage;
        this.statusMessage = statusMessage;
        this.recommendation = recommendation;
        this.organicPowderOutputKg = organicPowderOutputKg;
        this.nutrientSolutionOutputLiters = nutrientSolutionOutputLiters;
        this.startDate = startDate != null ? startDate : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    public static CompostBatchBuilder builder() {
        return new CompostBatchBuilder();
    }

    public static class CompostBatchBuilder {
        private Long id;
        private String batchName;
        private String wasteType;
        private Double quantityKg;
        private Double moisture;
        private Double temperature;
        private Double ph;
        private CompostStage stage;
        private String statusMessage;
        private String recommendation;
        private Double organicPowderOutputKg;
        private Double nutrientSolutionOutputLiters;
        private LocalDateTime startDate = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();

        public CompostBatchBuilder id(Long id) { this.id = id; return this; }
        public CompostBatchBuilder batchName(String batchName) { this.batchName = batchName; return this; }
        public CompostBatchBuilder wasteType(String wasteType) { this.wasteType = wasteType; return this; }
        public CompostBatchBuilder quantityKg(Double quantityKg) { this.quantityKg = quantityKg; return this; }
        public CompostBatchBuilder moisture(Double moisture) { this.moisture = moisture; return this; }
        public CompostBatchBuilder temperature(Double temperature) { this.temperature = temperature; return this; }
        public CompostBatchBuilder ph(Double ph) { this.ph = ph; return this; }
        public CompostBatchBuilder stage(CompostStage stage) { this.stage = stage; return this; }
        public CompostBatchBuilder statusMessage(String statusMessage) { this.statusMessage = statusMessage; return this; }
        public CompostBatchBuilder recommendation(String recommendation) { this.recommendation = recommendation; return this; }
        public CompostBatchBuilder organicPowderOutputKg(Double organicPowderOutputKg) { this.organicPowderOutputKg = organicPowderOutputKg; return this; }
        public CompostBatchBuilder nutrientSolutionOutputLiters(Double nutrientSolutionOutputLiters) { this.nutrientSolutionOutputLiters = nutrientSolutionOutputLiters; return this; }
        public CompostBatchBuilder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
        public CompostBatchBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public CompostBatch build() {
            return new CompostBatch(id, batchName, wasteType, quantityKg, moisture, temperature, ph, stage, statusMessage, recommendation, organicPowderOutputKg, nutrientSolutionOutputLiters, startDate, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBatchName() { return batchName; }
    public void setBatchName(String batchName) { this.batchName = batchName; }
    public String getWasteType() { return wasteType; }
    public void setWasteType(String wasteType) { this.wasteType = wasteType; }
    public Double getQuantityKg() { return quantityKg; }
    public void setQuantityKg(Double quantityKg) { this.quantityKg = quantityKg; }
    public Double getMoisture() { return moisture; }
    public void setMoisture(Double moisture) { this.moisture = moisture; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }
    public CompostStage getStage() { return stage; }
    public void setStage(CompostStage stage) { this.stage = stage; }
    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    public Double getOrganicPowderOutputKg() { return organicPowderOutputKg; }
    public void setOrganicPowderOutputKg(Double organicPowderOutputKg) { this.organicPowderOutputKg = organicPowderOutputKg; }
    public Double getNutrientSolutionOutputLiters() { return nutrientSolutionOutputLiters; }
    public void setNutrientSolutionOutputLiters(Double nutrientSolutionOutputLiters) { this.nutrientSolutionOutputLiters = nutrientSolutionOutputLiters; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
