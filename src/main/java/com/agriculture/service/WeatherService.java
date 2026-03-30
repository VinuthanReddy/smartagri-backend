package com.agriculture.service;

import com.agriculture.model.WeatherData;
import com.agriculture.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public WeatherData addWeatherData(WeatherData data) {
        data.setRecordedAt(LocalDateTime.now());
        return weatherDataRepository.save(data);
    }

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public Optional<WeatherData> getLatestByDistrict(String district) {
        return weatherDataRepository.findTopByDistrictOrderByRecordedAtDesc(district);
    }

    public List<WeatherData> getByDistrict(String district) {
        return weatherDataRepository.findByDistrict(district);
    }

    public List<WeatherData> getByState(String state) {
        return weatherDataRepository.findByState(state);
    }

    public WeatherData updateWeatherData(String id, WeatherData updated) {
        return weatherDataRepository.findById(id).map(w -> {
            w.setTemperature(updated.getTemperature());
            w.setHumidity(updated.getHumidity());
            w.setRainfall(updated.getRainfall());
            w.setWindSpeed(updated.getWindSpeed());
            w.setWeatherCondition(updated.getWeatherCondition());
            w.setAgriculturalAdvisory(updated.getAgriculturalAdvisory());
            w.setSowingAdvisory(updated.getSowingAdvisory());
            w.setIrrigationAdvisory(updated.getIrrigationAdvisory());
            w.setRecordedAt(LocalDateTime.now());
            return weatherDataRepository.save(w);
        }).orElseThrow(() -> new RuntimeException("Weather data not found"));
    }

    public void deleteWeatherData(String id) {
        weatherDataRepository.deleteById(id);
    }
}
