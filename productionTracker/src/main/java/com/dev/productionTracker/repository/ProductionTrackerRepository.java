package com.dev.productionTracker.repository;


import com.dev.productionTracker.model.ProductionTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductionTrackerRepository extends JpaRepository<ProductionTracker, Long> {
    @Query("SELECT p FROM ProductionTracker p WHERE p.isEnabled=true")
    List<ProductionTracker> getAllByIsEnabled();
}
