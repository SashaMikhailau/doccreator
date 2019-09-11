package com.mikhailau.model;

import java.math.BigDecimal;

public class InventoryItem {
    private final String name;
    private final String invNumber;
    private final BigDecimal amortizationCost;

    public InventoryItem(String name, String invNumber, BigDecimal amortizationCost) {
        this.name = name;
        this.invNumber = invNumber;
        this.amortizationCost = amortizationCost;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmortizationCost() {
        return amortizationCost;
    }

    public String getInvNumber() {
        return invNumber;
    }
}
