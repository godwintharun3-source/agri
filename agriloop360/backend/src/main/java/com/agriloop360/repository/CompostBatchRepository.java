package com.agriloop360.repository;

import com.agriloop360.entity.CompostBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompostBatchRepository extends JpaRepository<CompostBatch, Long> {
    List<CompostBatch> findAllByOrderByUpdatedAtDesc();
}
