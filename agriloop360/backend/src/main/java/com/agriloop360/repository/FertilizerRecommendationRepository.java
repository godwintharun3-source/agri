package com.agriloop360.repository;

import com.agriloop360.entity.FertilizerRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FertilizerRecommendationRepository extends JpaRepository<FertilizerRecommendation, Long> {
    Optional<FertilizerRecommendation> findTopByCropIdOrderByTimestampDesc(Long cropId);
    List<FertilizerRecommendation> findTop10ByOrderByTimestampDesc();
}
