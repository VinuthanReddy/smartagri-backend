package com.agriculture.controller;

import com.agriculture.model.WeatherData;
import com.agriculture.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping
    public ResponseEntity<?> addWeatherData(@RequestBody WeatherData data) {
        try {
            return ResponseEntity.ok(weatherService.addWeatherData(data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<WeatherData>> getAllWeatherData() {
        return ResponseEntity.ok(weatherService.getAllWeatherData());
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<WeatherData>> getByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(weatherService.getByDistrict(district));
    }

    @GetMapping("/district/{district}/latest")
    public ResponseEntity<?> getLatestByDistrict(@PathVariable String district) {
        return weatherService.getLatestByDistrict(district)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<WeatherData>> getByState(@PathVariable String state) {
        return ResponseEntity.ok(weatherService.getByState(state));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWeatherData(@PathVariable String id, @RequestBody WeatherData data) {
        try {
            return ResponseEntity.ok(weatherService.updateWeatherData(id, data));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWeatherData(@PathVariable String id) {
        weatherService.deleteWeatherData(id);
        return ResponseEntity.ok(Map.of("message", "Weather data deleted"));
    }
}
