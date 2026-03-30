package com.agriculture.service;

import com.agriculture.model.CropDisease;
import com.agriculture.repository.CropDiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CropDiseaseService {

    @Autowired
    private CropDiseaseRepository cropDiseaseRepository;

    public CropDisease reportDisease(CropDisease disease) {
        disease.setReportedAt(LocalDateTime.now());
        disease.setUpdatedAt(LocalDateTime.now());
        disease.setStatus("ACTIVE");
        return cropDiseaseRepository.save(disease);
    }

    public List<CropDisease> getAllDiseases() {
        return cropDiseaseRepository.findAll();
    }

    public Optional<CropDisease> getDiseaseById(String id) {
        return cropDiseaseRepository.findById(id);
    }

    public List<CropDisease> getDiseasesByCrop(String cropName) {
        return cropDiseaseRepository.findByCropName(cropName);
    }

    public List<CropDisease> getDiseasesBySeverity(String severity) {
        return cropDiseaseRepository.findBySeverity(severity);
    }

    public List<CropDisease> getDiseasesByFarmer(String farmerId) {
        return cropDiseaseRepository.findByReportedBy(farmerId);
    }

    public List<CropDisease> getActiveDiseases() {
        return cropDiseaseRepository.findByStatus("ACTIVE");
    }

    public CropDisease updateDisease(String id, CropDisease updated) {
        return cropDiseaseRepository.findById(id).map(disease -> {
            disease.setCropName(updated.getCropName());
            disease.setDiseaseName(updated.getDiseaseName());
            disease.setSeverity(updated.getSeverity());
            disease.setSymptoms(updated.getSymptoms());
            disease.setTreatments(updated.getTreatments());
            disease.setStatus(updated.getStatus());
            disease.setUpdatedAt(LocalDateTime.now());
            return cropDiseaseRepository.save(disease);
        }).orElseThrow(() -> new RuntimeException("Disease not found"));
    }

    public void deleteDisease(String id) {
        cropDiseaseRepository.deleteById(id);
    }
}
