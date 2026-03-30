package com.agriculture.controller;

import com.agriculture.model.ExpertConsultation;
import com.agriculture.service.ExpertConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultations")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpertConsultationController {

    @Autowired
    private ExpertConsultationService consultationService;

    @PostMapping
    public ResponseEntity<?> submitQuery(@RequestBody ExpertConsultation consultation) {
        try {
            return ResponseEntity.ok(consultationService.submitQuery(consultation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ExpertConsultation>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return consultationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<ExpertConsultation>> getByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(consultationService.getByFarmer(farmerId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ExpertConsultation>> getPending() {
        return ResponseEntity.ok(consultationService.getPendingConsultations());
    }

    @PutMapping("/{id}/respond")
    public ResponseEntity<?> respond(@PathVariable String id, @RequestBody Map<String, String> payload) {
        try {
            return ResponseEntity.ok(consultationService.respondToQuery(
                    id, payload.get("response"), payload.get("expertName")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConsultation(@PathVariable String id, @RequestBody ExpertConsultation consultation) {
        try {
            return ResponseEntity.ok(consultationService.updateConsultation(id, consultation));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.ok(Map.of("message", "Consultation deleted"));
    }
}
