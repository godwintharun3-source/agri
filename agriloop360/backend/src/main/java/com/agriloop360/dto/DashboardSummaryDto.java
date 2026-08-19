package com.agriloop360.dto;

import com.agriloop360.entity.*;
import java.util.List;

public class DashboardSummaryDto {
    private String waterStatus;
    private String soilStatus;
    private String fertilizerStatus;
    private String compostStatus;
    private String cropStatus;
    private String storageStatus;

    private WaterReading latestWaterReading;
    private SoilReading latestSoilReading;
    private CompostBatch latestCompostBatch;
    private FoodStorage latestFoodStorage;

    private Long activeCropsCount;
    private Long activeAlertsCount;

    private List<Alert> recentAlerts;
    private Boolean isSimulationActive;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(String waterStatus, String soilStatus, String fertilizerStatus, String compostStatus, String cropStatus, String storageStatus, WaterReading latestWaterReading, SoilReading latestSoilReading, CompostBatch latestCompostBatch, FoodStorage latestFoodStorage, Long activeCropsCount, Long activeAlertsCount, List<Alert> recentAlerts, Boolean isSimulationActive) {
        this.waterStatus = waterStatus;
        this.soilStatus = soilStatus;
        this.fertilizerStatus = fertilizerStatus;
        this.compostStatus = compostStatus;
        this.cropStatus = cropStatus;
        this.storageStatus = storageStatus;
        this.latestWaterReading = latestWaterReading;
        this.latestSoilReading = latestSoilReading;
        this.latestCompostBatch = latestCompostBatch;
        this.latestFoodStorage = latestFoodStorage;
        this.activeCropsCount = activeCropsCount;
        this.activeAlertsCount = activeAlertsCount;
        this.recentAlerts = recentAlerts;
        this.isSimulationActive = isSimulationActive;
    }

    public static DashboardSummaryDtoBuilder builder() {
        return new DashboardSummaryDtoBuilder();
    }

    public static class DashboardSummaryDtoBuilder {
        private String waterStatus;
        private String soilStatus;
        private String fertilizerStatus;
        private String compostStatus;
        private String cropStatus;
        private String storageStatus;
        private WaterReading latestWaterReading;
        private SoilReading latestSoilReading;
        private CompostBatch latestCompostBatch;
        private FoodStorage latestFoodStorage;
        private Long activeCropsCount;
        private Long activeAlertsCount;
        private List<Alert> recentAlerts;
        private Boolean isSimulationActive;

        public DashboardSummaryDtoBuilder waterStatus(String waterStatus) { this.waterStatus = waterStatus; return this; }
        public DashboardSummaryDtoBuilder soilStatus(String soilStatus) { this.soilStatus = soilStatus; return this; }
        public DashboardSummaryDtoBuilder fertilizerStatus(String fertilizerStatus) { this.fertilizerStatus = fertilizerStatus; return this; }
        public DashboardSummaryDtoBuilder compostStatus(String compostStatus) { this.compostStatus = compostStatus; return this; }
        public DashboardSummaryDtoBuilder cropStatus(String cropStatus) { this.cropStatus = cropStatus; return this; }
        public DashboardSummaryDtoBuilder storageStatus(String storageStatus) { this.storageStatus = storageStatus; return this; }
        public DashboardSummaryDtoBuilder latestWaterReading(WaterReading latestWaterReading) { this.latestWaterReading = latestWaterReading; return this; }
        public DashboardSummaryDtoBuilder latestSoilReading(SoilReading latestSoilReading) { this.latestSoilReading = latestSoilReading; return this; }
        public DashboardSummaryDtoBuilder latestCompostBatch(CompostBatch latestCompostBatch) { this.latestCompostBatch = latestCompostBatch; return this; }
        public DashboardSummaryDtoBuilder latestFoodStorage(FoodStorage latestFoodStorage) { this.latestFoodStorage = latestFoodStorage; return this; }
        public DashboardSummaryDtoBuilder activeCropsCount(Long activeCropsCount) { this.activeCropsCount = activeCropsCount; return this; }
        public DashboardSummaryDtoBuilder activeAlertsCount(Long activeAlertsCount) { this.activeAlertsCount = activeAlertsCount; return this; }
        public DashboardSummaryDtoBuilder recentAlerts(List<Alert> recentAlerts) { this.recentAlerts = recentAlerts; return this; }
        public DashboardSummaryDtoBuilder isSimulationActive(Boolean isSimulationActive) { this.isSimulationActive = isSimulationActive; return this; }

        public DashboardSummaryDto build() {
            return new DashboardSummaryDto(waterStatus, soilStatus, fertilizerStatus, compostStatus, cropStatus, storageStatus, latestWaterReading, latestSoilReading, latestCompostBatch, latestFoodStorage, activeCropsCount, activeAlertsCount, recentAlerts, isSimulationActive);
        }
    }

    public String getWaterStatus() { return waterStatus; }
    public void setWaterStatus(String waterStatus) { this.waterStatus = waterStatus; }
    public String getSoilStatus() { return soilStatus; }
    public void setSoilStatus(String soilStatus) { this.soilStatus = soilStatus; }
    public String getFertilizerStatus() { return fertilizerStatus; }
    public void setFertilizerStatus(String fertilizerStatus) { this.fertilizerStatus = fertilizerStatus; }
    public String getCompostStatus() { return compostStatus; }
    public void setCompostStatus(String compostStatus) { this.compostStatus = compostStatus; }
    public String getCropStatus() { return cropStatus; }
    public void setCropStatus(String cropStatus) { this.cropStatus = cropStatus; }
    public String getStorageStatus() { return storageStatus; }
    public void setStorageStatus(String storageStatus) { this.storageStatus = storageStatus; }
    public WaterReading getLatestWaterReading() { return latestWaterReading; }
    public void setLatestWaterReading(WaterReading latestWaterReading) { this.latestWaterReading = latestWaterReading; }
    public SoilReading getLatestSoilReading() { return latestSoilReading; }
    public void setLatestSoilReading(SoilReading latestSoilReading) { this.latestSoilReading = latestSoilReading; }
    public CompostBatch getLatestCompostBatch() { return latestCompostBatch; }
    public void setLatestCompostBatch(CompostBatch latestCompostBatch) { this.latestCompostBatch = latestCompostBatch; }
    public FoodStorage getLatestFoodStorage() { return latestFoodStorage; }
    public void setLatestFoodStorage(FoodStorage latestFoodStorage) { this.latestFoodStorage = latestFoodStorage; }
    public Long getActiveCropsCount() { return activeCropsCount; }
    public void setActiveCropsCount(Long activeCropsCount) { this.activeCropsCount = activeCropsCount; }
    public Long getActiveAlertsCount() { return activeAlertsCount; }
    public void setActiveAlertsCount(Long activeAlertsCount) { this.activeAlertsCount = activeAlertsCount; }
    public List<Alert> getRecentAlerts() { return recentAlerts; }
    public void setRecentAlerts(List<Alert> recentAlerts) { this.recentAlerts = recentAlerts; }
    public Boolean getIsSimulationActive() { return isSimulationActive; }
    public void setIsSimulationActive(Boolean isSimulationActive) { this.isSimulationActive = isSimulationActive; }
}
