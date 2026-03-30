package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "farm_plots")
public class FarmPlot {
    @Id private String id;
    private String farmerId;
    private String plotName;
    private String location;
    private String district;
    private double areaAcres;
    private String soilType;
    private String waterSource;
    private String irrigationType;
    private String currentCrop;
    private String plotStatus;
    private String ownershipType;
    private double leaseCostPerAcre;
    private String remarks;
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }
    public String getPlotName() { return plotName; }
    public void setPlotName(String plotName) { this.plotName = plotName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public double getAreaAcres() { return areaAcres; }
    public void setAreaAcres(double areaAcres) { this.areaAcres = areaAcres; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public String getWaterSource() { return waterSource; }
    public void setWaterSource(String waterSource) { this.waterSource = waterSource; }
    public String getIrrigationType() { return irrigationType; }
    public void setIrrigationType(String irrigationType) { this.irrigationType = irrigationType; }
    public String getCurrentCrop() { return currentCrop; }
    public void setCurrentCrop(String currentCrop) { this.currentCrop = currentCrop; }
    public String getPlotStatus() { return plotStatus; }
    public void setPlotStatus(String plotStatus) { this.plotStatus = plotStatus; }
    public String getOwnershipType() { return ownershipType; }
    public void setOwnershipType(String ownershipType) { this.ownershipType = ownershipType; }
    public double getLeaseCostPerAcre() { return leaseCostPerAcre; }
    public void setLeaseCostPerAcre(double leaseCostPerAcre) { this.leaseCostPerAcre = leaseCostPerAcre; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
