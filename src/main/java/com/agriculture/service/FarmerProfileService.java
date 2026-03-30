package com.agriculture.service;

import com.agriculture.model.FarmPlot;
import com.agriculture.model.Farmer;
import com.agriculture.model.FarmerTransaction;
import com.agriculture.repository.FarmPlotRepository;
import com.agriculture.repository.FarmerRepository;
import com.agriculture.repository.FarmerTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FarmerProfileService {

    @Autowired private FarmerRepository farmerRepository;
    @Autowired private FarmPlotRepository farmPlotRepository;
    @Autowired private FarmerTransactionRepository transactionRepository;

    // Farmer CRUD
    public List<Farmer> getAllFarmers() { return farmerRepository.findAll(); }

    public Optional<Farmer> getFarmerById(String id) { return farmerRepository.findById(id); }

    public Farmer updateFarmer(String id, Farmer updated) {
        return farmerRepository.findById(id).map(f -> {
            f.setName(updated.getName());
            f.setPhone(updated.getPhone());
            f.setVillage(updated.getVillage());
            f.setDistrict(updated.getDistrict());
            f.setState(updated.getState());
            f.setLandAreaAcres(updated.getLandAreaAcres());
            f.setCrops(updated.getCrops());
            f.setUpdatedAt(LocalDateTime.now());
            return farmerRepository.save(f);
        }).orElseThrow(() -> new RuntimeException("Farmer not found"));
    }

    public void deleteFarmer(String id) { farmerRepository.deleteById(id); }

    // Farm Plots
    public FarmPlot addPlot(FarmPlot plot) { return farmPlotRepository.save(plot); }

    public List<FarmPlot> getPlotsByFarmer(String farmerId) { return farmPlotRepository.findByFarmerId(farmerId); }

    public FarmPlot updatePlot(String id, FarmPlot updated) {
        return farmPlotRepository.findById(id).map(p -> {
            p.setPlotName(updated.getPlotName());
            p.setLocation(updated.getLocation());
            p.setAreaAcres(updated.getAreaAcres());
            p.setSoilType(updated.getSoilType());
            p.setWaterSource(updated.getWaterSource());
            p.setIrrigationType(updated.getIrrigationType());
            p.setCurrentCrop(updated.getCurrentCrop());
            p.setPlotStatus(updated.getPlotStatus());
            p.setOwnershipType(updated.getOwnershipType());
            p.setLeaseCostPerAcre(updated.getLeaseCostPerAcre());
            p.setRemarks(updated.getRemarks());
            return farmPlotRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Plot not found"));
    }

    public void deletePlot(String id) { farmPlotRepository.deleteById(id); }

    // Transactions
    public FarmerTransaction addTransaction(FarmerTransaction tx) {
        tx.setCreatedAt(LocalDateTime.now());
        return transactionRepository.save(tx);
    }

    public List<FarmerTransaction> getTransactionsByFarmer(String farmerId) {
        return transactionRepository.findByFarmerId(farmerId);
    }

    public List<FarmerTransaction> getTransactionsByType(String farmerId, String type) {
        return transactionRepository.findByFarmerIdAndType(farmerId, type);
    }

    public Map<String, Double> getFinancialSummary(String farmerId) {
        List<FarmerTransaction> all = transactionRepository.findByFarmerId(farmerId);
        double totalIncome = all.stream().filter(t -> "INCOME".equals(t.getType())).mapToDouble(FarmerTransaction::getAmount).sum();
        double totalExpense = all.stream().filter(t -> "EXPENSE".equals(t.getType())).mapToDouble(FarmerTransaction::getAmount).sum();
        Map<String, Double> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("netProfit", totalIncome - totalExpense);
        return summary;
    }

    public void deleteTransaction(String id) { transactionRepository.deleteById(id); }
}
