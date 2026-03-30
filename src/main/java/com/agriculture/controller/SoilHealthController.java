package com.agriculture.controller;

import com.agriculture.model.SoilHealth;
import com.agriculture.service.SoilHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/soil")
@CrossOrigin(origins = "http://localhost:3000")
public class SoilHealthController {

    @Autowired
    private SoilHealthService soilHealthService;

    @PostMapping
    public ResponseEntity<?> addSoilReport(@RequestBody SoilHealth soil) {
        try {
            return ResponseEntity.ok(soilHealthService.addSoilReport(soil));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<SoilHealth>> getAllReports() {
        return ResponseEntity.ok(soilHealthService.getAllReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return soilHealthService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<SoilHealth>> getByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(soilHealthService.getByFarmer(farmerId));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<SoilHealth>> getByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(soilHealthService.getByDistrict(district));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable String id, @RequestBody SoilHealth soil) {
        try {
            return ResponseEntity.ok(soilHealthService.updateReport(id, soil));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable String id) {
        soilHealthService.deleteReport(id);
        return ResponseEntity.ok(Map.of("message", "Soil report deleted"));
    }
}
