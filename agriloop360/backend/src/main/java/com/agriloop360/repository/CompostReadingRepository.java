package com.agriloop360.repository;

import com.agriloop360.entity.CompostReading;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompostReadingRepository extends JpaRepository<CompostReading, Long> {
    List<CompostReading> findTop20ByCompostBatchIdOrderByTimestampDesc(Long compostBatchId);
    List<CompostReading> findTop20ByOrderByTimestampDesc();
}
