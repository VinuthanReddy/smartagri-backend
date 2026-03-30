package com.agriculture.repository;

import com.agriculture.model.IrrigationSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IrrigationScheduleRepository extends MongoRepository<IrrigationSchedule, String> {
    List<IrrigationSchedule> findByFarmerId(String farmerId);
    List<IrrigationSchedule> findByFarmerIdAndStatus(String farmerId, String status);
    List<IrrigationSchedule> findByCropName(String cropName);
}
