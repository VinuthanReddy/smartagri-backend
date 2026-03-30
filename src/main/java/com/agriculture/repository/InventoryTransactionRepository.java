package com.agriculture.repository;

import com.agriculture.model.InventoryTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryTransactionRepository extends MongoRepository<InventoryTransaction, String> {
    List<InventoryTransaction> findByFarmerId(String farmerId);
    List<InventoryTransaction> findByItemId(String itemId);
    List<InventoryTransaction> findByTransactionType(String type);
    List<InventoryTransaction> findByFarmerIdAndTransactionType(String farmerId, String type);
}
