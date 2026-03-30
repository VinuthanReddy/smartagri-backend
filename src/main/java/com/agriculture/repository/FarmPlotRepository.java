package com.agriculture.repository;

import com.agriculture.model.FarmPlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FarmPlotRepository extends MongoRepository<FarmPlot, String> {
    List<FarmPlot> findByFarmerId(String farmerId);
    List<FarmPlot> findByFarmerIdAndPlotStatus(String farmerId, String status);
}
