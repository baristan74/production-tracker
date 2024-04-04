package com.dev.productionTracker.dto;

import com.dev.productionTracker.model.ProductType;
import com.dev.productionTracker.model.Quantity;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Builder
public record CreateProductionTrackerRequest (
        String productName,
        String size,
        String description,
        ProductType productType,
        Quantity quantity
) {
}
