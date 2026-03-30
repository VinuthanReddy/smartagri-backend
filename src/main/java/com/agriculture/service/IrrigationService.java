package com.agriculture.service;

import com.agriculture.model.IrrigationSchedule;
import com.agriculture.repository.IrrigationScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IrrigationService {

    @Autowired
    private IrrigationScheduleRepository irrigationScheduleRepository;

    public IrrigationSchedule createSchedule(IrrigationSchedule schedule) {
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setStatus("PENDING");
        return irrigationScheduleRepository.save(schedule);
    }

    public List<IrrigationSchedule> getAllSchedules() {
        return irrigationScheduleRepository.findAll();
    }

    public Optional<IrrigationSchedule> getById(String id) {
        return irrigationScheduleRepository.findById(id);
    }

    public List<IrrigationSchedule> getByFarmer(String farmerId) {
        return irrigationScheduleRepository.findByFarmerId(farmerId);
    }

    public List<IrrigationSchedule> getPendingByFarmer(String farmerId) {
        return irrigationScheduleRepository.findByFarmerIdAndStatus(farmerId, "PENDING");
    }

    public IrrigationSchedule updateSchedule(String id, IrrigationSchedule updated) {
        return irrigationScheduleRepository.findById(id).map(schedule -> {
            schedule.setCropName(updated.getCropName());
            schedule.setFieldAreaAcres(updated.getFieldAreaAcres());
            schedule.setIrrigationMethod(updated.getIrrigationMethod());
            schedule.setWaterRequirementLiters(updated.getWaterRequirementLiters());
            schedule.setScheduledDate(updated.getScheduledDate());
            schedule.setScheduledTime(updated.getScheduledTime());
            schedule.setFrequency(updated.getFrequency());
            schedule.setStatus(updated.getStatus());
            schedule.setNotes(updated.getNotes());
            return irrigationScheduleRepository.save(schedule);
        }).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public void deleteSchedule(String id) {
        irrigationScheduleRepository.deleteById(id);
    }
}
