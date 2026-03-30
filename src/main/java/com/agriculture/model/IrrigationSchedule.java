package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "irrigation_schedules")
public class IrrigationSchedule {
    @Id private String id;
    private String farmerId;
    private String cropName;
    private String fieldLocation;
    private double fieldAreaAcres;
    private String irrigationMethod;
    private double waterRequirementLiters;
    private LocalDate scheduledDate;
    private String scheduledTime;
    private String frequency;
    private String status;
    private String soilMoistureStatus;
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public String getFieldLocation() { return fieldLocation; }
    public void setFieldLocation(String fieldLocation) { this.fieldLocation = fieldLocation; }
    public double getFieldAreaAcres() { return fieldAreaAcres; }
    public void setFieldAreaAcres(double fieldAreaAcres) { this.fieldAreaAcres = fieldAreaAcres; }
    public String getIrrigationMethod() { return irrigationMethod; }
    public void setIrrigationMethod(String irrigationMethod) { this.irrigationMethod = irrigationMethod; }
    public double getWaterRequirementLiters() { return waterRequirementLiters; }
    public void setWaterRequirementLiters(double waterRequirementLiters) { this.waterRequirementLiters = waterRequirementLiters; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
    public String getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(String scheduledTime) { this.scheduledTime = scheduledTime; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSoilMoistureStatus() { return soilMoistureStatus; }
    public void setSoilMoistureStatus(String soilMoistureStatus) { this.soilMoistureStatus = soilMoistureStatus; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
