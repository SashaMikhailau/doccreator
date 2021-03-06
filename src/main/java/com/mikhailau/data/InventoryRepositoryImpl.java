package com.mikhailau.data;

import com.mikhailau.model.InventoryItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mikhailau.constants.PropertiesConstants.PATH_TO_CONFIG;

public class InventoryRepositoryImpl implements InventoryRepository {
    public static final String INVENTORY_LIST_PATH = PATH_TO_CONFIG +"oborudovanie.properties";
    private PropertiesRepository propertiesRepository = new PropertiesRepository();

    @Override
    public List<InventoryItem> getAll(){
        Map<String, String> itemsMap = propertiesRepository.getAllFromPropertyFile(INVENTORY_LIST_PATH);
        return itemsMap.entrySet()
                .stream()
                .map(this::getInventoryItemFromMapEntry)
                .collect(Collectors.toList());
    }

    private InventoryItem getInventoryItemFromMapEntry(Map.Entry<String, String> entry) {
        String invNumber = entry.getKey().trim();
        String[] splittedValue = entry.getValue().split("\\$");
        if(splittedValue.length<2){
            throw new IllegalArgumentException();
        }

        return new InventoryItem(splittedValue[0], invNumber, new BigDecimal(splittedValue[1]));
    }
}
