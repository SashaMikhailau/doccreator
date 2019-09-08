package com.mikhailau.model;

import com.mikhailau.constants.UiFieldsConstants;

import java.util.List;
import java.util.Map;

public class UiResultModel {
    private List<InventoryItem> inventoryItems;
    private Map<UiFieldsConstants.ExaminationType, Integer> examinationTypeTimeCosts;
    private Map<String, String> primaryFieldValues;

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public Map<UiFieldsConstants.ExaminationType, Integer> getExaminationTypeTimeCosts() {
        return examinationTypeTimeCosts;
    }

    public void setExaminationTypeTimeCosts(Map<UiFieldsConstants.ExaminationType, Integer> examinationTypeTimeCosts) {
        this.examinationTypeTimeCosts = examinationTypeTimeCosts;
    }

    public Map<String, String> getPrimaryFieldValues() {
        return primaryFieldValues;
    }

    public void setPrimaryFieldValues(Map<String,String> primaryFieldValues) {
        this.primaryFieldValues = primaryFieldValues;
    }
}
