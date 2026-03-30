package com.agriculture.controller;

import com.agriculture.model.IrrigationSchedule;
import com.agriculture.service.IrrigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/irrigation")
@CrossOrigin(origins = "http://localhost:3000")
public class IrrigationController {

    @Autowired
    private IrrigationService irrigationService;

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody IrrigationSchedule schedule) {
        try {
            return ResponseEntity.ok(irrigationService.createSchedule(schedule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<IrrigationSchedule>> getAllSchedules() {
        return ResponseEntity.ok(irrigationService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return irrigationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<IrrigationSchedule>> getByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(irrigationService.getByFarmer(farmerId));
    }

    @GetMapping("/farmer/{farmerId}/pending")
    public ResponseEntity<List<IrrigationSchedule>> getPendingByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(irrigationService.getPendingByFarmer(farmerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable String id, @RequestBody IrrigationSchedule schedule) {
        try {
            return ResponseEntity.ok(irrigationService.updateSchedule(id, schedule));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable String id) {
        irrigationService.deleteSchedule(id);
        return ResponseEntity.ok(Map.of("message", "Schedule deleted"));
    }
}
