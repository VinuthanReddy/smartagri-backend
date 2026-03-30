package com.agriculture.controller;

import com.agriculture.model.Crop;
import com.agriculture.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin(origins = "http://localhost:3000")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping
    public ResponseEntity<?> addCrop(@RequestBody Crop crop) {
        try { return ResponseEntity.ok(cropService.addCrop(crop)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAll() { return ResponseEntity.ok(cropService.getAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return cropService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<Crop>> getByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(cropService.getByFarmer(farmerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Crop>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(cropService.getByStatus(status));
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<List<Crop>> getBySeason(@PathVariable String season) {
        return ResponseEntity.ok(cropService.getBySeason(season));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCrop(@PathVariable String id, @RequestBody Crop crop) {
        try { return ResponseEntity.ok(cropService.updateCrop(id, crop)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrop(@PathVariable String id) {
        cropService.deleteCrop(id);
        return ResponseEntity.ok(Map.of("message", "Crop deleted"));
    }
}
