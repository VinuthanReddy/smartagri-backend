package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "crops")
public class Crop {
    @Id private String id;
    private String farmerId;
    private String farmerName;
    private String cropName;
    private String variety;
    private String season;
    private String fieldLocation;
    private double areaAcres;
    private LocalDate sowingDate;
    private LocalDate expectedHarvestDate;
    private LocalDate actualHarvestDate;
    private String growthStage;
    private String status;
    private double expectedYieldKg;
    private double actualYieldKg;
    private double yieldPerAcre;
    private double marketValuePerKg;
    private double totalRevenue;
    private String qualityGrade;
    private List<String> inputsUsed;
    private String remarks;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }
    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public String getVariety() { return variety; }
    public void setVariety(String variety) { this.variety = variety; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public String getFieldLocation() { return fieldLocation; }
    public void setFieldLocation(String fieldLocation) { this.fieldLocation = fieldLocation; }
    public double getAreaAcres() { return areaAcres; }
    public void setAreaAcres(double areaAcres) { this.areaAcres = areaAcres; }
    public LocalDate getSowingDate() { return sowingDate; }
    public void setSowingDate(LocalDate sowingDate) { this.sowingDate = sowingDate; }
    public LocalDate getExpectedHarvestDate() { return expectedHarvestDate; }
    public void setExpectedHarvestDate(LocalDate expectedHarvestDate) { this.expectedHarvestDate = expectedHarvestDate; }
    public LocalDate getActualHarvestDate() { return actualHarvestDate; }
    public void setActualHarvestDate(LocalDate actualHarvestDate) { this.actualHarvestDate = actualHarvestDate; }
    public String getGrowthStage() { return growthStage; }
    public void setGrowthStage(String growthStage) { this.growthStage = growthStage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getExpectedYieldKg() { return expectedYieldKg; }
    public void setExpectedYieldKg(double expectedYieldKg) { this.expectedYieldKg = expectedYieldKg; }
    public double getActualYieldKg() { return actualYieldKg; }
    public void setActualYieldKg(double actualYieldKg) { this.actualYieldKg = actualYieldKg; }
    public double getYieldPerAcre() { return yieldPerAcre; }
    public void setYieldPerAcre(double yieldPerAcre) { this.yieldPerAcre = yieldPerAcre; }
    public double getMarketValuePerKg() { return marketValuePerKg; }
    public void setMarketValuePerKg(double marketValuePerKg) { this.marketValuePerKg = marketValuePerKg; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public String getQualityGrade() { return qualityGrade; }
    public void setQualityGrade(String qualityGrade) { this.qualityGrade = qualityGrade; }
    public List<String> getInputsUsed() { return inputsUsed; }
    public void setInputsUsed(List<String> inputsUsed) { this.inputsUsed = inputsUsed; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
