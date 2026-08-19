package com.agriloop360.repository;

import com.agriloop360.entity.Crop;
import com.agriloop360.enumtype.CropType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByCropType(CropType cropType);
    Optional<Crop> findByNameIgnoreCase(String name);
}
