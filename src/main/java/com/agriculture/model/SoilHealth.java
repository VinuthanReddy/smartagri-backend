package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "soil_health")
public class SoilHealth {
    @Id private String id;
    private String farmerId;
    private String location;
    private String district;
    private double phLevel;
    private double nitrogenLevel;
    private double phosphorusLevel;
    private double potassiumLevel;
    private double organicMatter;
    private double moistureLevel;
    private String soilType;
    private String healthStatus;
    private List<String> recommendedCrops;
    private List<String> fertilizerRecommendations;
    private String remarks;
    private LocalDateTime testedAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public double getPhLevel() { return phLevel; }
    public void setPhLevel(double phLevel) { this.phLevel = phLevel; }
    public double getNitrogenLevel() { return nitrogenLevel; }
    public void setNitrogenLevel(double nitrogenLevel) { this.nitrogenLevel = nitrogenLevel; }
    public double getPhosphorusLevel() { return phosphorusLevel; }
    public void setPhosphorusLevel(double phosphorusLevel) { this.phosphorusLevel = phosphorusLevel; }
    public double getPotassiumLevel() { return potassiumLevel; }
    public void setPotassiumLevel(double potassiumLevel) { this.potassiumLevel = potassiumLevel; }
    public double getOrganicMatter() { return organicMatter; }
    public void setOrganicMatter(double organicMatter) { this.organicMatter = organicMatter; }
    public double getMoistureLevel() { return moistureLevel; }
    public void setMoistureLevel(double moistureLevel) { this.moistureLevel = moistureLevel; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
    public List<String> getRecommendedCrops() { return recommendedCrops; }
    public void setRecommendedCrops(List<String> recommendedCrops) { this.recommendedCrops = recommendedCrops; }
    public List<String> getFertilizerRecommendations() { return fertilizerRecommendations; }
    public void setFertilizerRecommendations(List<String> fertilizerRecommendations) { this.fertilizerRecommendations = fertilizerRecommendations; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getTestedAt() { return testedAt; }
    public void setTestedAt(LocalDateTime testedAt) { this.testedAt = testedAt; }
}
