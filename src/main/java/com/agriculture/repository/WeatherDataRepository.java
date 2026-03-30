package com.agriculture.repository;

import com.agriculture.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
    List<WeatherData> findByDistrict(String district);
    List<WeatherData> findByState(String state);
    Optional<WeatherData> findTopByDistrictOrderByRecordedAtDesc(String district);
}
