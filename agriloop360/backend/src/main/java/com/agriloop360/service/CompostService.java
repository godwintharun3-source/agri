package com.agriloop360.service;

import com.agriloop360.entity.CompostBatch;
import com.agriloop360.entity.CompostReading;
import com.agriloop360.enumtype.CompostStage;
import com.agriloop360.exception.ResourceNotFoundException;
import com.agriloop360.repository.CompostBatchRepository;
import com.agriloop360.repository.CompostReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompostService {

    private final CompostBatchRepository batchRepository;
    private final CompostReadingRepository readingRepository;
    private final RecommendationEngineService recommendationEngine;

    public CompostService(CompostBatchRepository batchRepository, CompostReadingRepository readingRepository, RecommendationEngineService recommendationEngine) {
        this.batchRepository = batchRepository;
        this.readingRepository = readingRepository;
        this.recommendationEngine = recommendationEngine;
    }

    public List<CompostBatch> getAllBatches() {
        return batchRepository.findAllByOrderByUpdatedAtDesc();
    }

    public CompostBatch getBatchById(Long id) {
        return batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compost batch not found with id: " + id));
    }

    public CompostBatch createBatch(CompostBatch batch) {
        if (batch.getStage() == null) batch.setStage(CompostStage.COLLECTION);
        if (batch.getStartDate() == null) batch.setStartDate(LocalDateTime.now());
        batch.setUpdatedAt(LocalDateTime.now());

        CompostBatch evaluated = recommendationEngine.evaluateCompost(batch);
        CompostBatch saved = batchRepository.save(evaluated);

        CompostReading reading = CompostReading.builder()
                .compostBatch(saved)
                .moisture(saved.getMoisture())
                .temperature(saved.getTemperature())
                .ph(saved.getPh())
                .stage(saved.getStage())
                .statusMsg(saved.getStatusMessage())
                .timestamp(LocalDateTime.now())
                .build();
        readingRepository.save(reading);

        return saved;
    }

    public CompostBatch updateBatchStage(Long id, CompostStage newStage) {
        CompostBatch batch = getBatchById(id);
        batch.setStage(newStage);
        batch.setUpdatedAt(LocalDateTime.now());

        CompostBatch evaluated = recommendationEngine.evaluateCompost(batch);
        CompostBatch saved = batchRepository.save(evaluated);

        CompostReading reading = CompostReading.builder()
                .compostBatch(saved)
                .moisture(saved.getMoisture())
                .temperature(saved.getTemperature())
                .ph(saved.getPh())
                .stage(saved.getStage())
                .statusMsg(saved.getStatusMessage())
                .timestamp(LocalDateTime.now())
                .build();
        readingRepository.save(reading);

        return saved;
    }

    public List<CompostReading> getBatchReadings(Long batchId) {
        return readingRepository.findTop20ByCompostBatchIdOrderByTimestampDesc(batchId);
    }
}
