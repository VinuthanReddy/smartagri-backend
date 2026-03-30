package com.agriculture.service;

import com.agriculture.model.InventoryItem;
import com.agriculture.model.InventoryTransaction;
import com.agriculture.repository.InventoryItemRepository;
import com.agriculture.repository.InventoryTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryItem addItem(InventoryItem item) {
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setTotalValue(item.getQuantityInStock() * item.getPricePerUnit());
        item.setStatus(computeStatus(item));
        return inventoryItemRepository.save(item);
    }

    public List<InventoryItem> getAll() {
        return inventoryItemRepository.findAll();
    }

    public Optional<InventoryItem> getById(String id) {
        return inventoryItemRepository.findById(id);
    }

    public List<InventoryItem> getByFarmer(String farmerId) {
        return inventoryItemRepository.findByFarmerId(farmerId);
    }

    public List<InventoryItem> getLowStockByFarmer(String farmerId) {
        return inventoryItemRepository.findByFarmerId(farmerId).stream()
                .filter(item -> item.getQuantityInStock() <= item.getMinimumStockLevel())
                .toList();
    }

    public List<InventoryItem> getByCategory(String category) {
        return inventoryItemRepository.findByCategory(category);
    }

    public InventoryItem recordUsage(String id, UsageRecord usage) {
        return inventoryItemRepository.findById(id).map(item -> {
            if (usage.getQuantityUsed() <= 0) {
                throw new RuntimeException("Usage quantity must be greater than zero");
            }
            if (usage.getQuantityUsed() > item.getQuantityInStock()) {
                throw new RuntimeException("Insufficient stock available");
            }

            item.setQuantityInStock(item.getQuantityInStock() - usage.getQuantityUsed());
            item.setTotalValue(item.getQuantityInStock() * item.getPricePerUnit());
            item.setStatus(computeStatus(item));
            item.setUpdatedAt(LocalDateTime.now());

            InventoryTransaction transaction = new InventoryTransaction();
            transaction.setFarmerId(item.getFarmerId());
            transaction.setItemId(item.getId());
            transaction.setItemName(item.getItemName());
            transaction.setTransactionType("USAGE");
            transaction.setQuantity(usage.getQuantityUsed());
            transaction.setPricePerUnit(item.getPricePerUnit());
            transaction.setTotalCost(usage.getQuantityUsed() * item.getPricePerUnit());
            transaction.setTransactionDate(usage.getUsedOn() != null ? usage.getUsedOn() : LocalDate.now());
            transaction.setUsedForCrop(usage.getUsedForCrop());
            transaction.setRemarks(usage.getRemarks());
            inventoryTransactionRepository.save(transaction);

            return inventoryItemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public InventoryItem updateItem(String id, InventoryItem updated) {
        return inventoryItemRepository.findById(id).map(item -> {
            item.setFarmerId(updated.getFarmerId());
            item.setItemName(updated.getItemName());
            item.setCategory(updated.getCategory());
            item.setBrand(updated.getBrand());
            item.setUnit(updated.getUnit());
            item.setQuantityInStock(updated.getQuantityInStock());
            item.setMinimumStockLevel(updated.getMinimumStockLevel());
            item.setPricePerUnit(updated.getPricePerUnit());
            item.setTotalValue(updated.getQuantityInStock() * updated.getPricePerUnit());
            item.setPurchaseDate(updated.getPurchaseDate());
            item.setExpiryDate(updated.getExpiryDate());
            item.setSupplier(updated.getSupplier());
            item.setStorageLocation(updated.getStorageLocation());
            item.setRemarks(updated.getRemarks());
            item.setStatus(computeStatus(item));
            item.setUpdatedAt(LocalDateTime.now());
            return inventoryItemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    private String computeStatus(InventoryItem item) {
        if (item.getQuantityInStock() <= 0) {
            return "OUT_OF_STOCK";
        }
        if (item.getExpiryDate() != null && item.getExpiryDate().isBefore(LocalDate.now())) {
            return "EXPIRED";
        }
        if (item.getQuantityInStock() <= item.getMinimumStockLevel()) {
            return "LOW_STOCK";
        }
        return "AVAILABLE";
    }

    public void deleteItem(String id) {
        inventoryItemRepository.deleteById(id);
    }

    public static class UsageRecord {
        private double quantityUsed;
        private LocalDate usedOn;
        private String usedForCrop;
        private String remarks;

        public double getQuantityUsed() {
            return quantityUsed;
        }

        public void setQuantityUsed(double quantityUsed) {
            this.quantityUsed = quantityUsed;
        }

        public LocalDate getUsedOn() {
            return usedOn;
        }

        public void setUsedOn(LocalDate usedOn) {
            this.usedOn = usedOn;
        }

        public String getUsedForCrop() {
            return usedForCrop;
        }

        public void setUsedForCrop(String usedForCrop) {
            this.usedForCrop = usedForCrop;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
