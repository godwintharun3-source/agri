package com.agriloop360.repository;

import com.agriloop360.entity.Alert;
import com.agriloop360.enumtype.ModuleName;
import com.agriloop360.enumtype.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByOrderByTimestampDesc();
    List<Alert> findTop10ByOrderByTimestampDesc();
    List<Alert> findByReadStatusFalseOrderByTimestampDesc();
    List<Alert> findBySeverityOrderByTimestampDesc(Severity severity);
    List<Alert> findByModuleOrderByTimestampDesc(ModuleName module);
    Long countByReadStatusFalse();
}
