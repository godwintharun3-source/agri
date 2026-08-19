package com.agriloop360.repository;

import com.agriloop360.entity.FoodStorageReading;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodStorageReadingRepository extends JpaRepository<FoodStorageReading, Long> {
    List<FoodStorageReading> findTop20ByFoodStorageIdOrderByTimestampDesc(Long foodStorageId);
    List<FoodStorageReading> findTop20ByOrderByTimestampDesc();
}
