package com.agriculture.service;

import com.agriculture.model.SoilHealth;
import com.agriculture.repository.SoilHealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SoilHealthService {

    @Autowired
    private SoilHealthRepository soilHealthRepository;

    public SoilHealth addSoilReport(SoilHealth soil) {
        soil.setTestedAt(LocalDateTime.now());
        soil.setHealthStatus(calculateHealthStatus(soil));
        return soilHealthRepository.save(soil);
    }

    private String calculateHealthStatus(SoilHealth soil) {
        int score = 0;
        if (soil.getPhLevel() >= 6.0 && soil.getPhLevel() <= 7.5) score++;
        if (soil.getNitrogenLevel() >= 50) score++;
        if (soil.getPhosphorusLevel() >= 25) score++;
        if (soil.getPotassiumLevel() >= 110) score++;
        if (soil.getOrganicMatter() >= 3) score++;

        if (score >= 5) return "EXCELLENT";
        else if (score >= 3) return "GOOD";
        else if (score >= 2) return "FAIR";
        else return "POOR";
    }

    public List<SoilHealth> getAllReports() {
        return soilHealthRepository.findAll();
    }

    public Optional<SoilHealth> getById(String id) {
        return soilHealthRepository.findById(id);
    }

    public List<SoilHealth> getByFarmer(String farmerId) {
        return soilHealthRepository.findByFarmerId(farmerId);
    }

    public List<SoilHealth> getByDistrict(String district) {
        return soilHealthRepository.findByDistrict(district);
    }

    public SoilHealth updateReport(String id, SoilHealth updated) {
        return soilHealthRepository.findById(id).map(soil -> {
            soil.setPhLevel(updated.getPhLevel());
            soil.setNitrogenLevel(updated.getNitrogenLevel());
            soil.setPhosphorusLevel(updated.getPhosphorusLevel());
            soil.setPotassiumLevel(updated.getPotassiumLevel());
            soil.setOrganicMatter(updated.getOrganicMatter());
            soil.setMoistureLevel(updated.getMoistureLevel());
            soil.setSoilType(updated.getSoilType());
            soil.setRecommendedCrops(updated.getRecommendedCrops());
            soil.setFertilizerRecommendations(updated.getFertilizerRecommendations());
            soil.setHealthStatus(calculateHealthStatus(updated));
            return soilHealthRepository.save(soil);
        }).orElseThrow(() -> new RuntimeException("Soil report not found"));
    }

    public void deleteReport(String id) {
        soilHealthRepository.deleteById(id);
    }
}
