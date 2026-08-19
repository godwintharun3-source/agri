package com.agriloop360.service;

import com.agriloop360.entity.Crop;
import com.agriloop360.enumtype.CropType;
import com.agriloop360.exception.ResourceNotFoundException;
import com.agriloop360.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CropService {

    private final CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public List<Crop> getCropsByType(CropType type) {
        return cropRepository.findByCropType(type);
    }

    public Crop getCropById(Long id) {
        return cropRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + id));
    }

    public Crop createCrop(Crop crop) {
        if (crop.getCreatedAt() == null) {
            crop.setCreatedAt(LocalDateTime.now());
        }
        return cropRepository.save(crop);
    }

    public Crop updateCrop(Long id, Crop updatedCrop) {
        Crop existing = getCropById(id);
        existing.setName(updatedCrop.getName());
        existing.setCropType(updatedCrop.getCropType());
        existing.setSoilType(updatedCrop.getSoilType());
        existing.setPlantingDate(updatedCrop.getPlantingDate());
        existing.setExpectedHarvestDate(updatedCrop.getExpectedHarvestDate());
        existing.setTargetPhMin(updatedCrop.getTargetPhMin());
        existing.setTargetPhMax(updatedCrop.getTargetPhMax());
        existing.setTargetMoistureMin(updatedCrop.getTargetMoistureMin());
        existing.setTargetMoistureMax(updatedCrop.getTargetMoistureMax());
        existing.setTargetN(updatedCrop.getTargetN());
        existing.setTargetP(updatedCrop.getTargetP());
        existing.setTargetK(updatedCrop.getTargetK());
        existing.setTargetTempMin(updatedCrop.getTargetTempMin());
        existing.setTargetTempMax(updatedCrop.getTargetTempMax());
        existing.setTargetHumidityMin(updatedCrop.getTargetHumidityMin());
        existing.setTargetHumidityMax(updatedCrop.getTargetHumidityMax());
        return cropRepository.save(existing);
    }

    public void deleteCrop(Long id) {
        Crop existing = getCropById(id);
        cropRepository.delete(existing);
    }
}
