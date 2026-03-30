package com.agriculture.service;

import com.agriculture.model.MarketPrice;
import com.agriculture.repository.MarketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MarketPriceService {

    @Autowired
    private MarketPriceRepository marketPriceRepository;

    public MarketPrice addPrice(MarketPrice price) {
        price.setUpdatedAt(LocalDateTime.now());
        return marketPriceRepository.save(price);
    }

    public List<MarketPrice> getAllPrices() {
        return marketPriceRepository.findAll();
    }

    public Optional<MarketPrice> getById(String id) {
        return marketPriceRepository.findById(id);
    }

    public List<MarketPrice> getByCrop(String cropName) {
        return marketPriceRepository.findByCropName(cropName);
    }

    public List<MarketPrice> getByDistrict(String district) {
        return marketPriceRepository.findByDistrict(district);
    }

    public List<MarketPrice> getByTrend(String trend) {
        return marketPriceRepository.findByTrend(trend);
    }

    public List<MarketPrice> getByCropAndDistrict(String cropName, String district) {
        return marketPriceRepository.findByCropNameAndDistrict(cropName, district);
    }

    public MarketPrice updatePrice(String id, MarketPrice updated) {
        return marketPriceRepository.findById(id).map(price -> {
            price.setMinPrice(updated.getMinPrice());
            price.setMaxPrice(updated.getMaxPrice());
            price.setModalPrice(updated.getModalPrice());
            price.setPriceChangePercent(updated.getPriceChangePercent());
            price.setTrend(updated.getTrend());
            price.setDemandLevel(updated.getDemandLevel());
            price.setUpdatedAt(LocalDateTime.now());
            return marketPriceRepository.save(price);
        }).orElseThrow(() -> new RuntimeException("Price not found"));
    }

    public void deletePrice(String id) {
        marketPriceRepository.deleteById(id);
    }
}
