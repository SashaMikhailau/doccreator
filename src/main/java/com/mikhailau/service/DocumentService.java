package com.mikhailau.service;

import com.mikhailau.data.InventoryRepository;
import com.mikhailau.data.InventoryRepositoryImpl;
import com.mikhailau.data.PropertiesRepository;
import com.mikhailau.model.InventoryItem;
import com.mikhailau.model.UiResultModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentService {
    public static final String SETTINGS_PATH = "src/main/resources/nastroiki.properties";
    private final Map<String, String> settings;
    private PropertiesRepository propertiesRepository = new PropertiesRepository();
    private InventoryRepository inventoryRepository = new InventoryRepositoryImpl();
    private FileCreatorService fileCreatorService = new FileCreatorService();

    public DocumentService() {
        this.settings = propertiesRepository.getAllFromPropertyFile(SETTINGS_PATH);;
    }

    public List<InventoryItem> getInventoryItems(){
        return inventoryRepository.getAll();
    }

    public Map<String,String> getSettings(){
        return new HashMap<>(settings);
    }



    public void pushData(UiResultModel dataFromUI){
        Map<String, String> copyOfSettings = getSettings();
        copyOfSettings.putAll(dataFromUI.getPrimaryFieldValues());
        fileCreatorService.createFiles(copyOfSettings);
    }


}
