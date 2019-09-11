package com.mikhailau.service;

import com.mikhailau.constants.PropertiesConstants;
import com.mikhailau.constants.UiFieldsConstants;
import com.mikhailau.model.InventoryItem;
import com.mikhailau.model.UiResultModel;
import com.mikhailau.util.TextUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.mikhailau.constants.CalculatedConstansts.*;
import static com.mikhailau.constants.UiFieldsConstants.*;

public class CalculatorService {
    private final UiResultModel uiResultModel;
    private final Map<String, String> settings;
    private final Integer time;
    private final boolean needTocountTimeCost;
    private BigDecimal total = BigDecimal.ZERO;

    public CalculatorService(UiResultModel uiResultModel,Map<String,String> settings) {
        this.settings = settings;
        this.uiResultModel = uiResultModel;
        time =
                Integer.parseInt(uiResultModel.getPrimaryFieldValues().get(TOTAL_HOURS_COUNT));
        InvestigationType investigationType =
                InvestigationType.getByName(uiResultModel.getPrimaryFieldValues().get(UiFieldsConstants.MATERIAL_TYPE));
        needTocountTimeCost = investigationType == InvestigationType.MAT_ADMINISTRATIVE;
    }

    public Map<String,String> calculateCosts(){
        Map<String, String> results = new HashMap<>();
        results.putAll(calculateInventoryCosts());
        results.putAll(calculateMiscTotal());
        if(needTocountTimeCost){
            results.putAll(calculateTimeCost());
        }
        results.putAll(calculateTotal());
        return results;
    }

    private Map<String, String> calculateTotal() {
        Map<String,String> results = new HashMap<>();
        results.put(TOTAL, TextUtils.formatBigDecimal(total));
        return results;
    }

    private Map<String,String> calculateInventoryCosts(){
        BigDecimal invCost = uiResultModel.getInventoryItems().stream()
                .map(InventoryItem::getAmortizationCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal invTotal = invCost.multiply(BigDecimal.valueOf(time));
        total = total.add(invTotal);
        Map<String,String> results = new HashMap<>();
        results.put(INV_TOTAL, TextUtils.formatBigDecimal(invTotal));
        results.put(INV_COST, TextUtils.formatBigDecimal(invCost));
        return results;
    }

    private Map<String,String> calculateMiscTotal(){
        Map<ExaminationType, Integer> examTypeTimeCosts = uiResultModel.getExaminationTypeTimeCosts();
        BigDecimal miscTotal = examTypeTimeCosts.entrySet()
                .stream()
                .map(this::countExamTypeCost)
                .reduce(new BigDecimal(0), BigDecimal::add);
        total = total.add(miscTotal);
        Map<String,String> results = new HashMap<>();
        results.put(MISC_COST, TextUtils.formatBigDecimal(miscTotal));
        return results;
    }

    private Map<String,String> calculateTimeCost(){
        BigDecimal hourCost = new BigDecimal(settings.get(PropertiesConstants.EXPERT_HOUR_COST));
        BigDecimal timeCost = hourCost.multiply(new BigDecimal(time));
        total = total.add(timeCost);
        Map<String,String> results = new HashMap<>();
        results.put(TIME_COST, TextUtils.formatBigDecimal(timeCost));
        return results;
    }



    private BigDecimal countExamTypeCost(Map.Entry<ExaminationType, Integer> entrySet) {
        String examTypeCostPerObject =
                settings.get(entrySet.getKey().name());
        Integer objectsCount = entrySet.getValue();
        return new BigDecimal(examTypeCostPerObject).multiply(new BigDecimal(objectsCount));
    }


}
