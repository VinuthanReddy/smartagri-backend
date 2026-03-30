package com.agriculture.controller;

import com.agriculture.model.MarketPrice;
import com.agriculture.service.MarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "http://localhost:3000")
public class MarketPriceController {

    @Autowired
    private MarketPriceService marketPriceService;

    @PostMapping
    public ResponseEntity<?> addPrice(@RequestBody MarketPrice price) {
        try {
            return ResponseEntity.ok(marketPriceService.addPrice(price));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<MarketPrice>> getAllPrices() {
        return ResponseEntity.ok(marketPriceService.getAllPrices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return marketPriceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/crop/{cropName}")
    public ResponseEntity<List<MarketPrice>> getByCrop(@PathVariable String cropName) {
        return ResponseEntity.ok(marketPriceService.getByCrop(cropName));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<MarketPrice>> getByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(marketPriceService.getByDistrict(district));
    }

    @GetMapping("/trend/{trend}")
    public ResponseEntity<List<MarketPrice>> getByTrend(@PathVariable String trend) {
        return ResponseEntity.ok(marketPriceService.getByTrend(trend));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MarketPrice>> getByCropAndDistrict(
            @RequestParam String crop, @RequestParam String district) {
        return ResponseEntity.ok(marketPriceService.getByCropAndDistrict(crop, district));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrice(@PathVariable String id, @RequestBody MarketPrice price) {
        try {
            return ResponseEntity.ok(marketPriceService.updatePrice(id, price));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrice(@PathVariable String id) {
        marketPriceService.deletePrice(id);
        return ResponseEntity.ok(Map.of("message", "Price deleted"));
    }
}
