package com.agriculture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "expert_consultations")
public class ExpertConsultation {
    @Id private String id;
    private String farmerId;
    private String farmerName;
    private String expertName;
    private String expertSpecialization;
    private String subject;
    private String query;
    private String response;
    private String status;
    private String priority;
    private LocalDateTime requestedAt = LocalDateTime.now();
    private LocalDateTime respondedAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }
    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }
    public String getExpertName() { return expertName; }
    public void setExpertName(String expertName) { this.expertName = expertName; }
    public String getExpertSpecialization() { return expertSpecialization; }
    public void setExpertSpecialization(String expertSpecialization) { this.expertSpecialization = expertSpecialization; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }
    public LocalDateTime getRespondedAt() { return respondedAt; }
    public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }
}
