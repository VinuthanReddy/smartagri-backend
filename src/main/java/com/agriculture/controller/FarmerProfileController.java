package com.agriculture.controller;

import com.agriculture.model.FarmPlot;
import com.agriculture.model.Farmer;
import com.agriculture.model.FarmerTransaction;
import com.agriculture.service.FarmerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/farmers")
@CrossOrigin(origins = "http://localhost:3000")
public class FarmerProfileController {

    @Autowired private FarmerProfileService farmerProfileService;

    // Farmers
    @GetMapping
    public ResponseEntity<List<Farmer>> getAllFarmers() { return ResponseEntity.ok(farmerProfileService.getAllFarmers()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFarmerById(@PathVariable String id) {
        return farmerProfileService.getFarmerById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFarmer(@PathVariable String id, @RequestBody Farmer farmer) {
        try { return ResponseEntity.ok(farmerProfileService.updateFarmer(id, farmer)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmer(@PathVariable String id) {
        farmerProfileService.deleteFarmer(id);
        return ResponseEntity.ok(Map.of("message", "Farmer deleted"));
    }

    // Farm Plots
    @PostMapping("/{farmerId}/plots")
    public ResponseEntity<?> addPlot(@PathVariable String farmerId, @RequestBody FarmPlot plot) {
        plot.setFarmerId(farmerId);
        return ResponseEntity.ok(farmerProfileService.addPlot(plot));
    }

    @GetMapping("/{farmerId}/plots")
    public ResponseEntity<List<FarmPlot>> getPlots(@PathVariable String farmerId) {
        return ResponseEntity.ok(farmerProfileService.getPlotsByFarmer(farmerId));
    }

    @PutMapping("/plots/{plotId}")
    public ResponseEntity<?> updatePlot(@PathVariable String plotId, @RequestBody FarmPlot plot) {
        try { return ResponseEntity.ok(farmerProfileService.updatePlot(plotId, plot)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }

    @DeleteMapping("/plots/{plotId}")
    public ResponseEntity<?> deletePlot(@PathVariable String plotId) {
        farmerProfileService.deletePlot(plotId);
        return ResponseEntity.ok(Map.of("message", "Plot deleted"));
    }

    // Transactions
    @PostMapping("/{farmerId}/transactions")
    public ResponseEntity<?> addTransaction(@PathVariable String farmerId, @RequestBody FarmerTransaction tx) {
        tx.setFarmerId(farmerId);
        return ResponseEntity.ok(farmerProfileService.addTransaction(tx));
    }

    @GetMapping("/{farmerId}/transactions")
    public ResponseEntity<List<FarmerTransaction>> getTransactions(@PathVariable String farmerId) {
        return ResponseEntity.ok(farmerProfileService.getTransactionsByFarmer(farmerId));
    }

    @GetMapping("/{farmerId}/transactions/summary")
    public ResponseEntity<Map<String, Double>> getSummary(@PathVariable String farmerId) {
        return ResponseEntity.ok(farmerProfileService.getFinancialSummary(farmerId));
    }

    @DeleteMapping("/transactions/{txId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String txId) {
        farmerProfileService.deleteTransaction(txId);
        return ResponseEntity.ok(Map.of("message", "Transaction deleted"));
    }
}
