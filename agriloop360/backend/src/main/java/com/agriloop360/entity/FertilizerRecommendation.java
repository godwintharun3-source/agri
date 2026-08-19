package com.agriloop360.entity;

import com.agriloop360.enumtype.OptimizationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fertilizer_recommendations")
public class FertilizerRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    private String nStatus;
    private String pStatus;
    private String kStatus;
    private String moistureStatus;
    private String phStatus;

    @Column(length = 1000)
    private String recommendationText;

    @Enumerated(EnumType.STRING)
    private OptimizationStatus optimizationStatus;

    private LocalDateTime timestamp = LocalDateTime.now();

    public FertilizerRecommendation() {}

    public FertilizerRecommendation(Long id, Crop crop, String nStatus, String pStatus, String kStatus, String moistureStatus, String phStatus, String recommendationText, OptimizationStatus optimizationStatus, LocalDateTime timestamp) {
        this.id = id;
        this.crop = crop;
        this.nStatus = nStatus;
        this.pStatus = pStatus;
        this.kStatus = kStatus;
        this.moistureStatus = moistureStatus;
        this.phStatus = phStatus;
        this.recommendationText = recommendationText;
        this.optimizationStatus = optimizationStatus;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static FertilizerRecommendationBuilder builder() {
        return new FertilizerRecommendationBuilder();
    }

    public static class FertilizerRecommendationBuilder {
        private Long id;
        private Crop crop;
        private String nStatus;
        private String pStatus;
        private String kStatus;
        private String moistureStatus;
        private String phStatus;
        private String recommendationText;
        private OptimizationStatus optimizationStatus;
        private LocalDateTime timestamp = LocalDateTime.now();

        public FertilizerRecommendationBuilder id(Long id) { this.id = id; return this; }
        public FertilizerRecommendationBuilder crop(Crop crop) { this.crop = crop; return this; }
        public FertilizerRecommendationBuilder nStatus(String nStatus) { this.nStatus = nStatus; return this; }
        public FertilizerRecommendationBuilder pStatus(String pStatus) { this.pStatus = pStatus; return this; }
        public FertilizerRecommendationBuilder kStatus(String kStatus) { this.kStatus = kStatus; return this; }
        public FertilizerRecommendationBuilder moistureStatus(String moistureStatus) { this.moistureStatus = moistureStatus; return this; }
        public FertilizerRecommendationBuilder phStatus(String phStatus) { this.phStatus = phStatus; return this; }
        public FertilizerRecommendationBuilder recommendationText(String recommendationText) { this.recommendationText = recommendationText; return this; }
        public FertilizerRecommendationBuilder optimizationStatus(OptimizationStatus optimizationStatus) { this.optimizationStatus = optimizationStatus; return this; }
        public FertilizerRecommendationBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public FertilizerRecommendation build() {
            return new FertilizerRecommendation(id, crop, nStatus, pStatus, kStatus, moistureStatus, phStatus, recommendationText, optimizationStatus, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Crop getCrop() { return crop; }
    public void setCrop(Crop crop) { this.crop = crop; }
    public String getNStatus() { return nStatus; }
    public void setNStatus(String nStatus) { this.nStatus = nStatus; }
    public String getPStatus() { return pStatus; }
    public void setPStatus(String pStatus) { this.pStatus = pStatus; }
    public String getKStatus() { return kStatus; }
    public void setKStatus(String kStatus) { this.kStatus = kStatus; }
    public String getMoistureStatus() { return moistureStatus; }
    public void setMoistureStatus(String moistureStatus) { this.moistureStatus = moistureStatus; }
    public String getPhStatus() { return phStatus; }
    public void setPhStatus(String phStatus) { this.phStatus = phStatus; }
    public String getRecommendationText() { return recommendationText; }
    public void setRecommendationText(String recommendationText) { this.recommendationText = recommendationText; }
    public OptimizationStatus getOptimizationStatus() { return optimizationStatus; }
    public void setOptimizationStatus(OptimizationStatus optimizationStatus) { this.optimizationStatus = optimizationStatus; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
