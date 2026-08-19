package com.agriloop360.entity;

import com.agriloop360.enumtype.WaterStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_readings")
public class WaterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ph;
    private Double tds;
    private Double temperature;
    private String turbidity;

    @Enumerated(EnumType.STRING)
    private WaterStatus status;

    @Column(length = 1000)
    private String recommendation;

    private LocalDateTime timestamp = LocalDateTime.now();

    public WaterReading() {}

    public WaterReading(Long id, Double ph, Double tds, Double temperature, String turbidity, WaterStatus status, String recommendation, LocalDateTime timestamp) {
        this.id = id;
        this.ph = ph;
        this.tds = tds;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.status = status;
        this.recommendation = recommendation;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static WaterReadingBuilder builder() {
        return new WaterReadingBuilder();
    }

    public static class WaterReadingBuilder {
        private Long id;
        private Double ph;
        private Double tds;
        private Double temperature;
        private String turbidity;
        private WaterStatus status;
        private String recommendation;
        private LocalDateTime timestamp = LocalDateTime.now();

        public WaterReadingBuilder id(Long id) { this.id = id; return this; }
        public WaterReadingBuilder ph(Double ph) { this.ph = ph; return this; }
        public WaterReadingBuilder tds(Double tds) { this.tds = tds; return this; }
        public WaterReadingBuilder temperature(Double temperature) { this.temperature = temperature; return this; }
        public WaterReadingBuilder turbidity(String turbidity) { this.turbidity = turbidity; return this; }
        public WaterReadingBuilder status(WaterStatus status) { this.status = status; return this; }
        public WaterReadingBuilder recommendation(String recommendation) { this.recommendation = recommendation; return this; }
        public WaterReadingBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public WaterReading build() {
            return new WaterReading(id, ph, tds, temperature, turbidity, status, recommendation, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }
    public Double getTds() { return tds; }
    public void setTds(Double tds) { this.tds = tds; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public String getTurbidity() { return turbidity; }
    public void setTurbidity(String turbidity) { this.turbidity = turbidity; }
    public WaterStatus getStatus() { return status; }
    public void setStatus(WaterStatus status) { this.status = status; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
