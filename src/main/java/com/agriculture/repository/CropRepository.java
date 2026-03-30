package com.agriculture.repository;

import com.agriculture.model.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CropRepository extends MongoRepository<Crop, String> {
    List<Crop> findByFarmerId(String farmerId);
    List<Crop> findByStatus(String status);
    List<Crop> findByCropName(String cropName);
    List<Crop> findBySeason(String season);
    List<Crop> findByFarmerIdAndStatus(String farmerId, String status);
}
