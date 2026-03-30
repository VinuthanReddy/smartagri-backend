package com.agriculture.repository;

import com.agriculture.model.SoilHealth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoilHealthRepository extends MongoRepository<SoilHealth, String> {
    List<SoilHealth> findByFarmerId(String farmerId);
    List<SoilHealth> findByDistrict(String district);
    List<SoilHealth> findByHealthStatus(String healthStatus);
}
