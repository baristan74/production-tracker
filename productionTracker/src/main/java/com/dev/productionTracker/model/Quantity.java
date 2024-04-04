package com.dev.productionTracker.model;

import lombok.Getter;

@Getter
public enum Quantity {

    PASSED("Passed"),
    FAILED("Failed");

    private final String quantityName;

    Quantity(String quantityName) {
        this.quantityName = quantityName;
    }
}
