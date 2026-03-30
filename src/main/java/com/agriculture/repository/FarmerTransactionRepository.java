package com.agriculture.repository;

import com.agriculture.model.FarmerTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerTransactionRepository extends MongoRepository<FarmerTransaction, String> {
    List<FarmerTransaction> findByFarmerId(String farmerId);
    List<FarmerTransaction> findByFarmerIdAndType(String farmerId, String type);
    List<FarmerTransaction> findByRelatedCropId(String cropId);
}
