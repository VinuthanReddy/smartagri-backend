package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "crop_diseases")
public class CropDisease {
    @Id private String id;
    private String cropName;
    private String diseaseName;
    private String severity;
    private String symptoms;
    private String cause;
    private List<String> treatments;
    private List<String> preventiveMeasures;
    private String affectedRegion;
    private String reportedBy;
    private String status;
    private String imageUrl;
    private LocalDateTime reportedAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public String getDiseaseName() { return diseaseName; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public String getCause() { return cause; }
    public void setCause(String cause) { this.cause = cause; }
    public List<String> getTreatments() { return treatments; }
    public void setTreatments(List<String> treatments) { this.treatments = treatments; }
    public List<String> getPreventiveMeasures() { return preventiveMeasures; }
    public void setPreventiveMeasures(List<String> preventiveMeasures) { this.preventiveMeasures = preventiveMeasures; }
    public String getAffectedRegion() { return affectedRegion; }
    public void setAffectedRegion(String affectedRegion) { this.affectedRegion = affectedRegion; }
    public String getReportedBy() { return reportedBy; }
    public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
