package com.agriloop360.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soil_readings")
public class SoilReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    private Double moisture;
    private Double ph;
    private Double nitrogen;
    private Double phosphorus;
    private Double potassium;

    private String status;

    @Column(length = 1000)
    private String recommendation;

    private LocalDateTime timestamp = LocalDateTime.now();

    public SoilReading() {}

    public SoilReading(Long id, Crop crop, Double moisture, Double ph, Double nitrogen, Double phosphorus, Double potassium, String status, String recommendation, LocalDateTime timestamp) {
        this.id = id;
        this.crop = crop;
        this.moisture = moisture;
        this.ph = ph;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.status = status;
        this.recommendation = recommendation;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static SoilReadingBuilder builder() {
        return new SoilReadingBuilder();
    }

    public static class SoilReadingBuilder {
        private Long id;
        private Crop crop;
        private Double moisture;
        private Double ph;
        private Double nitrogen;
        private Double phosphorus;
        private Double potassium;
        private String status;
        private String recommendation;
        private LocalDateTime timestamp = LocalDateTime.now();

        public SoilReadingBuilder id(Long id) { this.id = id; return this; }
        public SoilReadingBuilder crop(Crop crop) { this.crop = crop; return this; }
        public SoilReadingBuilder moisture(Double moisture) { this.moisture = moisture; return this; }
        public SoilReadingBuilder ph(Double ph) { this.ph = ph; return this; }
        public SoilReadingBuilder nitrogen(Double nitrogen) { this.nitrogen = nitrogen; return this; }
        public SoilReadingBuilder phosphorus(Double phosphorus) { this.phosphorus = phosphorus; return this; }
        public SoilReadingBuilder potassium(Double potassium) { this.potassium = potassium; return this; }
        public SoilReadingBuilder status(String status) { this.status = status; return this; }
        public SoilReadingBuilder recommendation(String recommendation) { this.recommendation = recommendation; return this; }
        public SoilReadingBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public SoilReading build() {
            return new SoilReading(id, crop, moisture, ph, nitrogen, phosphorus, potassium, status, recommendation, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Crop getCrop() { return crop; }
    public void setCrop(Crop crop) { this.crop = crop; }
    public Double getMoisture() { return moisture; }
    public void setMoisture(Double moisture) { this.moisture = moisture; }
    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }
    public Double getNitrogen() { return nitrogen; }
    public void setNitrogen(Double nitrogen) { this.nitrogen = nitrogen; }
    public Double getPhosphorus() { return phosphorus; }
    public void setPhosphorus(Double phosphorus) { this.phosphorus = phosphorus; }
    public Double getPotassium() { return potassium; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
