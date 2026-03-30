package com.agriculture.controller;

import com.agriculture.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    @Autowired
    private AiService aiService;

    // 1. AI Chatbot
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> body) {
        String message = body.getOrDefault("message", "");
        String history = body.getOrDefault("history", "");
        String response = aiService.chat(message, history);
        return ResponseEntity.ok(Map.of("response", response));
    }

    // 2. Disease Diagnosis
    @PostMapping("/diagnose-disease")
    public ResponseEntity<Map<String, String>> diagnoseDısease(@RequestBody Map<String, String> body) {
        String result = aiService.diagnoseCropDisease(
            body.getOrDefault("cropName", ""),
            body.getOrDefault("symptoms", ""),
            body.getOrDefault("location", ""),
            body.getOrDefault("season", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 3. Crop Recommendation
    @PostMapping("/recommend-crops")
    public ResponseEntity<Map<String, String>> recommendCrops(@RequestBody Map<String, String> body) {
        String result = aiService.recommendCrops(
            body.getOrDefault("soilType", ""),
            body.getOrDefault("district", ""),
            body.getOrDefault("season", ""),
            body.getOrDefault("waterAvailability", ""),
            body.getOrDefault("budgetRange", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 4. Irrigation Advisory
    @PostMapping("/irrigation-advice")
    public ResponseEntity<Map<String, String>> irrigationAdvice(@RequestBody Map<String, Object> body) {
        String result = aiService.getIrrigationAdvice(
            (String) body.getOrDefault("cropName", ""),
            (String) body.getOrDefault("growthStage", ""),
            (String) body.getOrDefault("soilType", ""),
            toDouble(body.get("temperature")),
            toDouble(body.get("humidity")),
            toDouble(body.get("rainfall")),
            (String) body.getOrDefault("irrigationMethod", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 5. Soil Analysis
    @PostMapping("/soil-analysis")
    public ResponseEntity<Map<String, String>> soilAnalysis(@RequestBody Map<String, Object> body) {
        String result = aiService.analyzeSoil(
            toDouble(body.get("ph")),
            toDouble(body.get("nitrogen")),
            toDouble(body.get("phosphorus")),
            toDouble(body.get("potassium")),
            toDouble(body.get("organicMatter")),
            (String) body.getOrDefault("soilType", ""),
            (String) body.getOrDefault("targetCrop", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 6. Market Insights
    @PostMapping("/market-insights")
    public ResponseEntity<Map<String, String>> marketInsights(@RequestBody Map<String, Object> body) {
        String result = aiService.getMarketInsights(
            (String) body.getOrDefault("cropName", ""),
            toDouble(body.get("currentPrice")),
            (String) body.getOrDefault("district", ""),
            (String) body.getOrDefault("season", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 7. Weather Advisory
    @PostMapping("/weather-advisory")
    public ResponseEntity<Map<String, String>> weatherAdvisory(@RequestBody Map<String, Object> body) {
        String result = aiService.getWeatherAdvisory(
            (String) body.getOrDefault("district", ""),
            toDouble(body.get("temperature")),
            toDouble(body.get("humidity")),
            toDouble(body.get("rainfall")),
            (String) body.getOrDefault("weatherCondition", ""),
            (String) body.getOrDefault("season", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 8. Pest Risk Prediction
    @PostMapping("/pest-prediction")
    public ResponseEntity<Map<String, String>> pestPrediction(@RequestBody Map<String, Object> body) {
        String result = aiService.predictPestRisk(
            (String) body.getOrDefault("cropName", ""),
            (String) body.getOrDefault("growthStage", ""),
            toDouble(body.get("temperature")),
            toDouble(body.get("humidity")),
            (String) body.getOrDefault("recentWeather", ""),
            (String) body.getOrDefault("region", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 9. Yield Prediction
    @PostMapping("/yield-prediction")
    public ResponseEntity<Map<String, String>> yieldPrediction(@RequestBody Map<String, Object> body) {
        String result = aiService.predictYield(
            (String) body.getOrDefault("cropName", ""),
            (String) body.getOrDefault("variety", ""),
            toDouble(body.get("areaAcres")),
            (String) body.getOrDefault("soilHealth", ""),
            (String) body.getOrDefault("irrigationType", ""),
            (String) body.getOrDefault("season", ""),
            (String) body.getOrDefault("fertilizerUsed", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    // 10. Government Schemes
    @PostMapping("/govt-schemes")
    public ResponseEntity<Map<String, String>> govtSchemes(@RequestBody Map<String, String> body) {
        String result = aiService.getGovernmentSchemes(
            body.getOrDefault("state", ""),
            body.getOrDefault("cropName", ""),
            body.getOrDefault("landHoldingAcres", ""),
            body.getOrDefault("farmerCategory", "")
        );
        return ResponseEntity.ok(Map.of("result", result));
    }

    private double toDouble(Object val) {
        if (val == null) return 0.0;
        if (val instanceof Number) return ((Number) val).doubleValue();
        try { return Double.parseDouble(val.toString()); } catch (Exception e) { return 0.0; }
    }
}
