package com.agriculture.repository;

import com.agriculture.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends MongoRepository<InventoryItem, String> {
    List<InventoryItem> findByFarmerId(String farmerId);
    List<InventoryItem> findByCategory(String category);
    List<InventoryItem> findByStatus(String status);
    List<InventoryItem> findByFarmerIdAndStatus(String farmerId, String status);
}
