package com.agriloop360.entity;

import com.agriloop360.enumtype.CompostStage;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compost_readings")
public class CompostReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compost_batch_id")
    private CompostBatch compostBatch;

    private Double moisture;
    private Double temperature;
    private Double ph;

    @Enumerated(EnumType.STRING)
    private CompostStage stage;

    private String statusMsg;

    private LocalDateTime timestamp = LocalDateTime.now();

    public CompostReading() {}

    public CompostReading(Long id, CompostBatch compostBatch, Double moisture, Double temperature, Double ph, CompostStage stage, String statusMsg, LocalDateTime timestamp) {
        this.id = id;
        this.compostBatch = compostBatch;
        this.moisture = moisture;
        this.temperature = temperature;
        this.ph = ph;
        this.stage = stage;
        this.statusMsg = statusMsg;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static CompostReadingBuilder builder() {
        return new CompostReadingBuilder();
    }

    public static class CompostReadingBuilder {
        private Long id;
        private CompostBatch compostBatch;
        private Double moisture;
        private Double temperature;
        private Double ph;
        private CompostStage stage;
        private String statusMsg;
        private LocalDateTime timestamp = LocalDateTime.now();

        public CompostReadingBuilder id(Long id) { this.id = id; return this; }
        public CompostReadingBuilder compostBatch(CompostBatch compostBatch) { this.compostBatch = compostBatch; return this; }
        public CompostReadingBuilder moisture(Double moisture) { this.moisture = moisture; return this; }
        public CompostReadingBuilder temperature(Double temperature) { this.temperature = temperature; return this; }
        public CompostReadingBuilder ph(Double ph) { this.ph = ph; return this; }
        public CompostReadingBuilder stage(CompostStage stage) { this.stage = stage; return this; }
        public CompostReadingBuilder statusMsg(String statusMsg) { this.statusMsg = statusMsg; return this; }
        public CompostReadingBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public CompostReading build() {
            return new CompostReading(id, compostBatch, moisture, temperature, ph, stage, statusMsg, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CompostBatch getCompostBatch() { return compostBatch; }
    public void setCompostBatch(CompostBatch compostBatch) { this.compostBatch = compostBatch; }
    public Double getMoisture() { return moisture; }
    public void setMoisture(Double moisture) { this.moisture = moisture; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }
    public CompostStage getStage() { return stage; }
    public void setStage(CompostStage stage) { this.stage = stage; }
    public String getStatusMsg() { return statusMsg; }
    public void setStatusMsg(String statusMsg) { this.statusMsg = statusMsg; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
