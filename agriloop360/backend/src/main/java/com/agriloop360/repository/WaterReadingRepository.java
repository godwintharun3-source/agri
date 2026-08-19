package com.agriloop360.repository;

import com.agriloop360.entity.WaterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WaterReadingRepository extends JpaRepository<WaterReading, Long> {
    Optional<WaterReading> findTopByOrderByTimestampDesc();
    List<WaterReading> findTop20ByOrderByTimestampDesc();
}
