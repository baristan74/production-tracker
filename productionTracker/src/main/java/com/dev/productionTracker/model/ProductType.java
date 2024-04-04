package com.dev.productionTracker.model;

import lombok.Getter;

@Getter
public enum ProductType {

    PRESS_MACHINE_PRODUCT("Press Machine Product"),
    OVEN_PRODUCT("Oven Product");

    private final String typeName;

    ProductType(String typeName) {
        this.typeName = typeName;
    }

}
