package com.agriculture.repository;

import com.agriculture.model.MarketPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketPriceRepository extends MongoRepository<MarketPrice, String> {
    List<MarketPrice> findByCropName(String cropName);
    List<MarketPrice> findByDistrict(String district);
    List<MarketPrice> findByState(String state);
    List<MarketPrice> findByTrend(String trend);
    List<MarketPrice> findByCropNameAndDistrict(String cropName, String district);
}
