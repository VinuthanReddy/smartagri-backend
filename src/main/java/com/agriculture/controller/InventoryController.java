package com.agriculture.controller;

import com.agriculture.model.InventoryItem;
import com.agriculture.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody InventoryItem item) {
        try {
            return ResponseEntity.ok(inventoryService.addItem(item));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return inventoryService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<InventoryItem>> getByFarmer(@PathVariable String farmerId) {
        return ResponseEntity.ok(inventoryService.getByFarmer(farmerId));
    }

    @GetMapping("/farmer/{farmerId}/low-stock")
    public ResponseEntity<List<InventoryItem>> getLowStock(@PathVariable String farmerId) {
        return ResponseEntity.ok(inventoryService.getLowStockByFarmer(farmerId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<InventoryItem>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(inventoryService.getByCategory(category));
    }

    @PostMapping("/{id}/use")
    public ResponseEntity<?> recordUsage(@PathVariable String id, @RequestBody InventoryService.UsageRecord usage) {
        try {
            return ResponseEntity.ok(inventoryService.recordUsage(id, usage));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable String id, @RequestBody InventoryItem item) {
        try {
            return ResponseEntity.ok(inventoryService.updateItem(id, item));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable String id) {
        inventoryService.deleteItem(id);
        return ResponseEntity.ok(Map.of("message", "Item deleted"));
    }
}
