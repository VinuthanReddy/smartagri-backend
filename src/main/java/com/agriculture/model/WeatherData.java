package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "weather_data")
public class WeatherData {
    @Id private String id;
    private String location;
    private String district;
    private String state;
    private double temperature;
    private double humidity;
    private double rainfall;
    private double windSpeed;
    private String weatherCondition;
    private String forecastType;
    private String agriculturalAdvisory;
    private String sowingAdvisory;
    private String irrigationAdvisory;
    private LocalDateTime recordedAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }
    public double getRainfall() { return rainfall; }
    public void setRainfall(double rainfall) { this.rainfall = rainfall; }
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    public String getWeatherCondition() { return weatherCondition; }
    public void setWeatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; }
    public String getForecastType() { return forecastType; }
    public void setForecastType(String forecastType) { this.forecastType = forecastType; }
    public String getAgriculturalAdvisory() { return agriculturalAdvisory; }
    public void setAgriculturalAdvisory(String agriculturalAdvisory) { this.agriculturalAdvisory = agriculturalAdvisory; }
    public String getSowingAdvisory() { return sowingAdvisory; }
    public void setSowingAdvisory(String sowingAdvisory) { this.sowingAdvisory = sowingAdvisory; }
    public String getIrrigationAdvisory() { return irrigationAdvisory; }
    public void setIrrigationAdvisory(String irrigationAdvisory) { this.irrigationAdvisory = irrigationAdvisory; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
