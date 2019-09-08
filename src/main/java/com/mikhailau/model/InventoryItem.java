package com.mikhailau.model;

import java.math.BigDecimal;

public class InventoryItem {
    private final String name;

    public String getName() {
        return name;
    }

    public BigDecimal getAmortizationCost() {
        return amortizationCost;
    }

    private final BigDecimal amortizationCost;

    public InventoryItem(String name, BigDecimal amortizationCost) {
        this.name = name;
        this.amortizationCost = amortizationCost;
    }
}
