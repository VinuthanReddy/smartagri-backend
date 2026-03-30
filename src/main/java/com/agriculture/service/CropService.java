package com.agriculture.service;

import com.agriculture.model.Crop;
import com.agriculture.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    public Crop addCrop(Crop crop) {
        crop.setCreatedAt(LocalDateTime.now());
        crop.setUpdatedAt(LocalDateTime.now());
        if (crop.getStatus() == null) crop.setStatus("ACTIVE");
        if (crop.getGrowthStage() == null) crop.setGrowthStage("SOWING");
        return cropRepository.save(crop);
    }

    public List<Crop> getAll() { return cropRepository.findAll(); }

    public Optional<Crop> getById(String id) { return cropRepository.findById(id); }

    public List<Crop> getByFarmer(String farmerId) { return cropRepository.findByFarmerId(farmerId); }

    public List<Crop> getByStatus(String status) { return cropRepository.findByStatus(status); }

    public List<Crop> getBySeason(String season) { return cropRepository.findBySeason(season); }

    public Crop updateCrop(String id, Crop updated) {
        return cropRepository.findById(id).map(crop -> {
            crop.setCropName(updated.getCropName());
            crop.setFarmerId(updated.getFarmerId());
            crop.setFarmerName(updated.getFarmerName());
            crop.setVariety(updated.getVariety());
            crop.setSeason(updated.getSeason());
            crop.setFieldLocation(updated.getFieldLocation());
            crop.setAreaAcres(updated.getAreaAcres());
            crop.setSowingDate(updated.getSowingDate());
            crop.setExpectedHarvestDate(updated.getExpectedHarvestDate());
            crop.setActualHarvestDate(updated.getActualHarvestDate());
            crop.setGrowthStage(updated.getGrowthStage());
            crop.setStatus(updated.getStatus());
            crop.setExpectedYieldKg(updated.getExpectedYieldKg());
            crop.setActualYieldKg(updated.getActualYieldKg());
            crop.setMarketValuePerKg(updated.getMarketValuePerKg());
            crop.setQualityGrade(updated.getQualityGrade());
            crop.setInputsUsed(updated.getInputsUsed());
            double revenue = updated.getActualYieldKg() * updated.getMarketValuePerKg();
            crop.setTotalRevenue(revenue);
            crop.setYieldPerAcre(updated.getAreaAcres() > 0 ? updated.getActualYieldKg() / updated.getAreaAcres() : 0);
            crop.setRemarks(updated.getRemarks());
            crop.setUpdatedAt(LocalDateTime.now());
            return cropRepository.save(crop);
        }).orElseThrow(() -> new RuntimeException("Crop not found"));
    }

    public void deleteCrop(String id) { cropRepository.deleteById(id); }
}
