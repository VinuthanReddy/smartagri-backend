package com.agriculture.controller;

import com.agriculture.model.CropDisease;
import com.agriculture.service.CropDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin(origins = "http://localhost:3000")
public class CropDiseaseController {

    @Autowired
    private CropDiseaseService cropDiseaseService;

    @PostMapping
    public ResponseEntity<?> reportDisease(@RequestBody CropDisease disease) {
        try {
            return ResponseEntity.ok(cropDiseaseService.reportDisease(disease));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CropDisease>> getAllDiseases() {
        return ResponseEntity.ok(cropDiseaseService.getAllDiseases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiseaseById(@PathVariable String id) {
        return cropDiseaseService.getDiseaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/crop/{cropName}")
    public ResponseEntity<List<CropDisease>> getByCrop(@PathVariable String cropName) {
        return ResponseEntity.ok(cropDiseaseService.getDiseasesByCrop(cropName));
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<CropDisease>> getBySeverity(@PathVariable String severity) {
        return ResponseEntity.ok(cropDiseaseService.getDiseasesBySeverity(severity));
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<CropDisease>> getByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(cropDiseaseService.getDiseasesByFarmer(farmerId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<CropDisease>> getActiveDiseases() {
        return ResponseEntity.ok(cropDiseaseService.getActiveDiseases());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDisease(@PathVariable String id, @RequestBody CropDisease disease) {
        try {
            return ResponseEntity.ok(cropDiseaseService.updateDisease(id, disease));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDisease(@PathVariable String id) {
        cropDiseaseService.deleteDisease(id);
        return ResponseEntity.ok(Map.of("message", "Disease report deleted successfully"));
    }
}
