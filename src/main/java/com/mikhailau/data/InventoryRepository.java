package com.mikhailau.data;

import com.mikhailau.model.InventoryItem;

import java.util.List;

public interface InventoryRepository {
    List<InventoryItem> getAll();
}
