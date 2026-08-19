package com.agriloop360.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_storage_readings")
public class FoodStorageReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_storage_id")
    private FoodStorage foodStorage;

    private Double temperature;
    private Double humidity;
    private Boolean uvcActive;

    private LocalDateTime timestamp = LocalDateTime.now();

    public FoodStorageReading() {}

    public FoodStorageReading(Long id, FoodStorage foodStorage, Double temperature, Double humidity, Boolean uvcActive, LocalDateTime timestamp) {
        this.id = id;
        this.foodStorage = foodStorage;
        this.temperature = temperature;
        this.humidity = humidity;
        this.uvcActive = uvcActive;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static FoodStorageReadingBuilder builder() {
        return new FoodStorageReadingBuilder();
    }

    public static class FoodStorageReadingBuilder {
        private Long id;
        private FoodStorage foodStorage;
        private Double temperature;
        private Double humidity;
        private Boolean uvcActive;
        private LocalDateTime timestamp = LocalDateTime.now();

        public FoodStorageReadingBuilder id(Long id) { this.id = id; return this; }
        public FoodStorageReadingBuilder foodStorage(FoodStorage foodStorage) { this.foodStorage = foodStorage; return this; }
        public FoodStorageReadingBuilder temperature(Double temperature) { this.temperature = temperature; return this; }
        public FoodStorageReadingBuilder humidity(Double humidity) { this.humidity = humidity; return this; }
        public FoodStorageReadingBuilder uvcActive(Boolean uvcActive) { this.uvcActive = uvcActive; return this; }
        public FoodStorageReadingBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public FoodStorageReading build() {
            return new FoodStorageReading(id, foodStorage, temperature, humidity, uvcActive, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public FoodStorage getFoodStorage() { return foodStorage; }
    public void setFoodStorage(FoodStorage foodStorage) { this.foodStorage = foodStorage; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Boolean getUvcActive() { return uvcActive; }
    public void setUvcActive(Boolean uvcActive) { this.uvcActive = uvcActive; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
