package com.agriloop360.entity;

import com.agriloop360.enumtype.StorageSafetyStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_storage")
public class FoodStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storageName;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    private Double temperature;
    private Double humidity;
    private Boolean uvcActive;

    @Enumerated(EnumType.STRING)
    private StorageSafetyStatus safetyStatus;

    @Column(length = 1000)
    private String warningMessage;

    private LocalDateTime timestamp = LocalDateTime.now();

    public FoodStorage() {}

    public FoodStorage(Long id, String storageName, Crop crop, Double temperature, Double humidity, Boolean uvcActive, StorageSafetyStatus safetyStatus, String warningMessage, LocalDateTime timestamp) {
        this.id = id;
        this.storageName = storageName;
        this.crop = crop;
        this.temperature = temperature;
        this.humidity = humidity;
        this.uvcActive = uvcActive;
        this.safetyStatus = safetyStatus;
        this.warningMessage = warningMessage;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static FoodStorageBuilder builder() {
        return new FoodStorageBuilder();
    }

    public static class FoodStorageBuilder {
        private Long id;
        private String storageName;
        private Crop crop;
        private Double temperature;
        private Double humidity;
        private Boolean uvcActive;
        private StorageSafetyStatus safetyStatus;
        private String warningMessage;
        private LocalDateTime timestamp = LocalDateTime.now();

        public FoodStorageBuilder id(Long id) { this.id = id; return this; }
        public FoodStorageBuilder storageName(String storageName) { this.storageName = storageName; return this; }
        public FoodStorageBuilder crop(Crop crop) { this.crop = crop; return this; }
        public FoodStorageBuilder temperature(Double temperature) { this.temperature = temperature; return this; }
        public FoodStorageBuilder humidity(Double humidity) { this.humidity = humidity; return this; }
        public FoodStorageBuilder uvcActive(Boolean uvcActive) { this.uvcActive = uvcActive; return this; }
        public FoodStorageBuilder safetyStatus(StorageSafetyStatus safetyStatus) { this.safetyStatus = safetyStatus; return this; }
        public FoodStorageBuilder warningMessage(String warningMessage) { this.warningMessage = warningMessage; return this; }
        public FoodStorageBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public FoodStorage build() {
            return new FoodStorage(id, storageName, crop, temperature, humidity, uvcActive, safetyStatus, warningMessage, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStorageName() { return storageName; }
    public void setStorageName(String storageName) { this.storageName = storageName; }
    public Crop getCrop() { return crop; }
    public void setCrop(Crop crop) { this.crop = crop; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Boolean getUvcActive() { return uvcActive; }
    public void setUvcActive(Boolean uvcActive) { this.uvcActive = uvcActive; }
    public StorageSafetyStatus getSafetyStatus() { return safetyStatus; }
    public void setSafetyStatus(StorageSafetyStatus safetyStatus) { this.safetyStatus = safetyStatus; }
    public String getWarningMessage() { return warningMessage; }
    public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
