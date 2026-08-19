package com.agriloop360.repository;

import com.agriloop360.entity.FoodStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodStorageRepository extends JpaRepository<FoodStorage, Long> {
    List<FoodStorage> findAllByOrderByTimestampDesc();
}
