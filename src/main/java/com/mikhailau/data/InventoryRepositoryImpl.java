package com.mikhailau.data;

import com.mikhailau.model.InventoryItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryRepositoryImpl implements InventoryRepository {
    public static final String INVENTORY_LIST_PATH = "src/main/resources/oborudovanie.properties";
    private PropertiesRepository propertiesRepository = new PropertiesRepository();

    @Override
    public List<InventoryItem> getAll(){
        Map<String, String> itemsMap = propertiesRepository.getAllFromPropertyFile(INVENTORY_LIST_PATH);
        return itemsMap.entrySet()
                .stream()
                .map(entry -> new InventoryItem(entry.getKey(), new BigDecimal(entry.getValue())))
                .collect(Collectors.toList());
    }
}
