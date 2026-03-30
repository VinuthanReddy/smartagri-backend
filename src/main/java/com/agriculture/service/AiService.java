package com.agriculture.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiService {

    @Value("${anthropic.api.key:YOUR_ANTHROPIC_API_KEY}")
    private String anthropicApiKey;

    @Value("${ai.provider:GEMINI}")
    private String provider;

    private static final String ANTHROPIC_API_URL = "https://api.anthropic.com/v1/messages";
    @Value("${anthropic.model:claude-sonnet-4-20250514}")
    private String anthropicModel;

    @Value("${openai.api.key:}")
    private String openAiApiKey;

    @Value("${openai.model:gpt-4.1-mini}")
    private String openAiModel;

    @Value("${gemini.api.key:${GEMINI_API_KEY:${GOOGLE_API_KEY:}}}")
    private String geminiApiKey;

    @Value("${gemini.model:gemini-2.5-flash}")
    private String geminiModel;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String GEMINI_API_URL_TEMPLATE =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String callModel(String systemPrompt, String userMessage) {
        try {
            String selectedProvider = resolveProvider();
            return switch (selectedProvider) {
                case "ANTHROPIC" -> callAnthropic(systemPrompt, userMessage);
                case "OPENAI" -> callOpenAi(systemPrompt, userMessage);
                case "GEMINI" -> callGemini(systemPrompt, userMessage);
                default -> """
                    AI service is not configured. Set a backend Gemini key with GEMINI_API_KEY or GOOGLE_API_KEY.
                    """.trim();
            };
        } catch (HttpClientErrorException.Unauthorized e) {
            return "AI service authentication failed. Check whether the configured provider API key is valid and active.";
        } catch (Exception e) {
            return "AI service temporarily unavailable. Please try again. Error: " + e.getMessage();
        }
    }

    private String resolveProvider() {
        String normalizedProvider = provider == null ? "AUTO" : provider.trim().toUpperCase(Locale.ROOT);
        if (!"AUTO".equals(normalizedProvider)) {
            return normalizedProvider;
        }
        if (isConfigured(anthropicApiKey, "YOUR_ANTHROPIC_API_KEY")) {
            return "ANTHROPIC";
        }
        if (isConfigured(openAiApiKey, null)) {
            return "OPENAI";
        }
        if (isConfigured(geminiApiKey, null)) {
            return "GEMINI";
        }
        return "NONE";
    }

    private boolean isConfigured(String value, String placeholderPrefix) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return placeholderPrefix == null || !value.startsWith(placeholderPrefix);
    }

    private String callAnthropic(String systemPrompt, String userMessage) throws Exception {
        if (!isConfigured(anthropicApiKey, "YOUR_ANTHROPIC_API_KEY")) {
            return "Anthropic AI is not configured. Set a valid ANTHROPIC_API_KEY.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", anthropicApiKey);
        headers.set("anthropic-version", "2023-06-01");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", userMessage);

        Map<String, Object> body = new HashMap<>();
        body.put("model", anthropicModel);
        body.put("max_tokens", 1024);
        body.put("system", systemPrompt);
        body.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(ANTHROPIC_API_URL, HttpMethod.POST, entity, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        return root.path("content").get(0).path("text").asText();
    }

    private String callOpenAi(String systemPrompt, String userMessage) throws Exception {
        if (!isConfigured(openAiApiKey, null)) {
            return "OpenAI is not configured. Set a valid OPENAI_API_KEY.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userMessage)
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", messages);
        body.put("temperature", 0.3);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        return root.path("choices").get(0).path("message").path("content").asText();
    }

    private String callGemini(String systemPrompt, String userMessage) throws Exception {
        if (!isConfigured(geminiApiKey, null)) {
            return "Gemini AI is not configured. Set a valid GEMINI_API_KEY or GOOGLE_API_KEY for the backend server.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = String.format(GEMINI_API_URL_TEMPLATE, geminiModel, geminiApiKey);

        Map<String, Object> structuredBody = new HashMap<>();
        structuredBody.put("systemInstruction", Map.of(
                "parts", List.of(Map.of("text", systemPrompt))
        ));
        structuredBody.put("contents", List.of(
                Map.of("role", "user", "parts", List.of(Map.of("text", userMessage)))
        ));
        structuredBody.put("generationConfig", Map.of(
                "temperature", 0.3,
                "maxOutputTokens", 1024
        ));

        String structuredResponse = executeGeminiRequest(url, headers, structuredBody);
        if (structuredResponse != null) {
            return structuredResponse;
        }

        Map<String, Object> fallbackBody = new HashMap<>();
        fallbackBody.put("contents", List.of(
                Map.of("parts", List.of(Map.of("text", systemPrompt + "\n\n" + userMessage)))
        ));
        fallbackBody.put("generationConfig", Map.of(
                "temperature", 0.3,
                "maxOutputTokens", 1024
        ));

        String fallbackResponse = executeGeminiRequest(url, headers, fallbackBody);
        if (fallbackResponse != null) {
            return fallbackResponse;
        }

        throw new IllegalStateException("Gemini returned an empty response.");
    }

    private String executeGeminiRequest(String url, HttpHeaders headers, Map<String, Object> body) throws Exception {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode candidates = root.path("candidates");
        if (!candidates.isArray() || candidates.isEmpty()) {
            return null;
        }

        JsonNode parts = candidates.get(0).path("content").path("parts");
        if (!parts.isArray() || parts.isEmpty()) {
            return null;
        }

        StringBuilder combined = new StringBuilder();
        for (JsonNode part : parts) {
            String text = part.path("text").asText();
            if (!text.isBlank()) {
                if (!combined.isEmpty()) {
                    combined.append('\n');
                }
                combined.append(text);
            }
        }

        return combined.isEmpty() ? null : combined.toString();
    }

    // 1. AI Farming Chatbot
    public String chat(String userMessage, String conversationHistory) {
        String system = """
            You are AgriBot, an expert AI assistant for Indian farmers. You provide practical, actionable advice on:
            - Crop selection, cultivation, and management
            - Soil health, fertilizers, and nutrients
            - Pest and disease management
            - Irrigation techniques and schedules
            - Weather interpretation for farming
            - Market prices and selling strategies
            - Government schemes and subsidies for farmers
            Always respond in simple, clear language. If asked in Hindi or regional languages, respond accordingly.
            Keep responses concise (under 200 words) and practical.
            """;
        String prompt = conversationHistory.isBlank()
                ? userMessage
                : "Previous conversation:\n" + conversationHistory + "\n\nFarmer says: " + userMessage;
        return callModel(system, prompt);
    }

    // 2. AI Crop Disease Diagnosis
    public String diagnoseCropDisease(String cropName, String symptoms, String location, String season) {
        String system = """
            You are an expert plant pathologist and agricultural scientist specializing in Indian crops.
            Diagnose crop diseases based on symptoms and provide structured recommendations.
            Always respond in this exact JSON format:
            {
              "disease_name": "...",
              "confidence": "High/Medium/Low",
              "description": "...",
              "cause": "...",
              "severity": "Critical/High/Medium/Low",
              "immediate_actions": ["action1", "action2"],
              "treatments": ["treatment1", "treatment2"],
              "preventive_measures": ["measure1", "measure2"],
              "estimated_recovery_days": 0
            }
            """;
        String prompt = String.format(
            "Crop: %s\nSymptoms: %s\nLocation: %s\nSeason: %s\nDiagnose this disease and provide treatment recommendations.",
            cropName, symptoms, location, season);
        return callModel(system, prompt);
    }

    // 3. AI Crop Recommendation
    public String recommendCrops(String soilType, String district, String season, String waterAvailability, String budgetRange) {
        String system = """
            You are an agricultural expert specializing in crop planning for Indian farmers.
            Recommend the best crops based on soil, location, season, and resources.
            Always respond in this exact JSON format:
            {
              "top_crops": [
                {
                  "crop_name": "...",
                  "variety": "...",
                  "expected_yield_per_acre": "...",
                  "market_demand": "High/Medium/Low",
                  "profit_potential": "High/Medium/Low",
                  "sowing_time": "...",
                  "harvest_time": "...",
                  "water_requirement": "High/Medium/Low",
                  "reasons": ["reason1", "reason2"]
                }
              ],
              "general_advice": "...",
              "soil_preparation_tips": "..."
            }
            Return top 3 crop recommendations.
            """;
        String prompt = String.format(
            "Soil type: %s\nDistrict/Region: %s\nSeason: %s\nWater availability: %s\nBudget range: %s\nRecommend the best crops to grow.",
            soilType, district, season, waterAvailability, budgetRange);
        return callModel(system, prompt);
    }

    // 4. AI Irrigation Advisory
    public String getIrrigationAdvice(String cropName, String growthStage, String soilType,
                                       double temperature, double humidity, double rainfall, String irrigationMethod) {
        String system = """
            You are an irrigation engineer and agronomist expert for Indian farming conditions.
            Provide precise, data-driven irrigation advice.
            Always respond in this exact JSON format:
            {
              "irrigation_needed": true/false,
              "urgency": "Immediate/Within 24hrs/Within 48hrs/Not needed",
              "recommended_water_quantity_liters_per_acre": 0,
              "irrigation_frequency": "...",
              "best_time_to_irrigate": "...",
              "method_recommendation": "...",
              "water_saving_tips": ["tip1", "tip2"],
              "weather_consideration": "...",
              "next_irrigation_date": "in X days"
            }
            """;
        String prompt = String.format(
            "Crop: %s\nGrowth Stage: %s\nSoil Type: %s\nTemperature: %.1f°C\nHumidity: %.1f%%\nRecent Rainfall: %.1fmm\nCurrent Irrigation Method: %s\nProvide irrigation advice.",
            cropName, growthStage, soilType, temperature, humidity, rainfall, irrigationMethod);
        return callModel(system, prompt);
    }

    // 5. AI Soil Health Analysis
    public String analyzeSoil(double ph, double nitrogen, double phosphorus, double potassium,
                               double organicMatter, String soilType, String targetCrop) {
        String system = """
            You are a soil scientist specializing in Indian agricultural soils.
            Analyze soil parameters and provide comprehensive recommendations.
            Always respond in this exact JSON format:
            {
              "overall_health": "Excellent/Good/Fair/Poor",
              "health_score": 0,
              "deficiencies": ["deficiency1", "deficiency2"],
              "excesses": ["excess1"],
              "fertilizer_recommendations": [
                {"fertilizer": "...", "quantity_per_acre": "...", "timing": "..."}
              ],
              "soil_amendments": ["amendment1", "amendment2"],
              "best_crops_for_this_soil": ["crop1", "crop2", "crop3"],
              "ph_correction": "...",
              "organic_matter_advice": "...",
              "expected_improvement_weeks": 0,
              "priority_actions": ["action1", "action2"]
            }
            """;
        String prompt = String.format(
            "pH: %.1f\nNitrogen: %.1f mg/kg\nPhosphorus: %.1f mg/kg\nPotassium: %.1f mg/kg\nOrganic Matter: %.1f%%\nSoil Type: %s\nTarget Crop: %s\nAnalyze and provide recommendations.",
            ph, nitrogen, phosphorus, potassium, organicMatter, soilType, targetCrop);
        return callModel(system, prompt);
    }

    // 6. AI Market Price Insights
    public String getMarketInsights(String cropName, double currentPrice, String district, String season) {
        String system = """
            You are an agricultural market analyst with expertise in Indian commodity markets.
            Provide price trend analysis and selling strategy recommendations.
            Always respond in this exact JSON format:
            {
              "price_trend": "Rising/Falling/Stable",
              "market_outlook": "...",
              "expected_price_range_next_30_days": {"min": 0, "max": 0, "unit": "Rs/quintal"},
              "best_time_to_sell": "...",
              "demand_factors": ["factor1", "factor2"],
              "supply_factors": ["factor1", "factor2"],
              "selling_strategy": "...",
              "alternative_markets": ["market1", "market2"],
              "value_addition_suggestions": ["suggestion1", "suggestion2"],
              "risk_factors": ["risk1", "risk2"]
            }
            """;
        String prompt = String.format(
            "Crop: %s\nCurrent Price: Rs %.2f/quintal\nDistrict: %s\nSeason: %s\nAnalyze market trends and provide selling advice.",
            cropName, currentPrice, district, season);
        return callModel(system, prompt);
    }

    // 7. AI Weather Advisory for Farming
    public String getWeatherAdvisory(String district, double temperature, double humidity,
                                      double rainfall, String weatherCondition, String season) {
        String system = """
            You are an agrometeorologist providing weather-based farming advisories for Indian farmers.
            Analyze weather data and give actionable farming advice.
            Always respond in this exact JSON format:
            {
              "farming_risk_level": "High/Medium/Low",
              "immediate_advisories": ["advisory1", "advisory2"],
              "crop_protection_measures": ["measure1", "measure2"],
              "recommended_farm_activities": ["activity1", "activity2"],
              "activities_to_avoid": ["activity1", "activity2"],
              "pest_disease_risk": "...",
              "irrigation_advisory": "...",
              "harvesting_advisory": "...",
              "next_7_days_outlook": "..."
            }
            """;
        String prompt = String.format(
            "District: %s\nTemperature: %.1f°C\nHumidity: %.1f%%\nRainfall: %.1fmm\nWeather: %s\nSeason: %s\nProvide comprehensive farming weather advisory.",
            district, temperature, humidity, rainfall, weatherCondition, season);
        return callModel(system, prompt);
    }

    // 8. AI Pest Risk Prediction
    public String predictPestRisk(String cropName, String growthStage, double temperature,
                                   double humidity, String recentWeather, String region) {
        String system = """
            You are an entomologist and plant protection expert specializing in Indian crop pests.
            Predict pest risks based on conditions and provide preventive recommendations.
            Always respond in this exact JSON format:
            {
              "high_risk_pests": [
                {"pest_name": "...", "risk_level": "High/Medium/Low", "symptoms_to_watch": "..."}
              ],
              "overall_pest_pressure": "High/Medium/Low",
              "risk_factors": ["factor1", "factor2"],
              "preventive_sprays": [
                {"product": "...", "dosage": "...", "timing": "..."}
              ],
              "monitoring_advice": "...",
              "biological_control_options": ["option1", "option2"],
              "favorable_period_for_pests": "...",
              "economic_threshold_advisory": "..."
            }
            """;
        String prompt = String.format(
            "Crop: %s\nGrowth Stage: %s\nTemperature: %.1f°C\nHumidity: %.1f%%\nRecent Weather: %s\nRegion: %s\nPredict pest risks and prevention measures.",
            cropName, growthStage, temperature, humidity, recentWeather, region);
        return callModel(system, prompt);
    }

    // 9. AI Yield Prediction
    public String predictYield(String cropName, String variety, double areaAcres, String soilHealth,
                                String irrigationType, String season, String fertilizerUsed) {
        String system = """
            You are an agricultural data scientist specializing in crop yield prediction for Indian farms.
            Predict yield and provide optimization recommendations.
            Always respond in this exact JSON format:
            {
              "estimated_yield_kg_per_acre": 0,
              "total_estimated_yield_kg": 0,
              "confidence_level": "High/Medium/Low",
              "yield_quality_grade": "A/B/C",
              "key_yield_factors": ["factor1", "factor2"],
              "optimization_tips": ["tip1", "tip2"],
              "comparison_to_average": "...",
              "estimated_revenue_rs": 0,
              "harvest_readiness_indicator": "...",
              "post_harvest_recommendations": ["rec1", "rec2"]
            }
            """;
        String prompt = String.format(
            "Crop: %s\nVariety: %s\nArea: %.1f acres\nSoil Health: %s\nIrrigation: %s\nSeason: %s\nFertilizer: %s\nPredict yield and provide optimization tips.",
            cropName, variety, areaAcres, soilHealth, irrigationType, season, fertilizerUsed);
        return callModel(system, prompt);
    }

    // 10. AI Government Schemes Advisor
    public String getGovernmentSchemes(String state, String cropName, String landHoldingAcres, String farmerCategory) {
        String system = """
            You are an expert on Indian government agricultural schemes and subsidies.
            Identify relevant schemes and guide farmers on eligibility and application.
            Always respond in this exact JSON format:
            {
              "applicable_schemes": [
                {
                  "scheme_name": "...",
                  "ministry": "...",
                  "benefit": "...",
                  "eligibility": "...",
                  "how_to_apply": "...",
                  "documents_required": ["doc1", "doc2"]
                }
              ],
              "total_potential_benefit_rs": "...",
              "nearest_application_center": "...",
              "important_deadlines": "...",
              "helpline": "..."
            }
            """;
        String prompt = String.format(
            "State: %s\nCrop: %s\nLand Holding: %s acres\nFarmer Category: %s\nIdentify applicable government schemes and subsidies.",
            state, cropName, landHoldingAcres, farmerCategory);
        return callModel(system, prompt);
    }
}
