package com.agriculture.repository;

import com.agriculture.model.CropDisease;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropDiseaseRepository extends MongoRepository<CropDisease, String> {
    List<CropDisease> findByCropName(String cropName);
    List<CropDisease> findBySeverity(String severity);
    List<CropDisease> findByStatus(String status);
    List<CropDisease> findByReportedBy(String farmerId);
    List<CropDisease> findByAffectedRegion(String region);
}
