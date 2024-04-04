package com.dev.productionTracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name="productionTracker")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String size;
    private String description;
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private Quantity quantity;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        isEnabled = true;
    }

}
