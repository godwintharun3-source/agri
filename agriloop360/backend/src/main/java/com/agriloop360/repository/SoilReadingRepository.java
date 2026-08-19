package com.agriloop360.repository;

import com.agriloop360.entity.SoilReading;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SoilReadingRepository extends JpaRepository<SoilReading, Long> {
    Optional<SoilReading> findTopByOrderByTimestampDesc();
    Optional<SoilReading> findTopByCropIdOrderByTimestampDesc(Long cropId);
    List<SoilReading> findTop20ByOrderByTimestampDesc();
    List<SoilReading> findTop20ByCropIdOrderByTimestampDesc(Long cropId);
}
