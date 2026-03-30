package com.agriculture.service;

import com.agriculture.model.ExpertConsultation;
import com.agriculture.repository.ExpertConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpertConsultationService {

    @Autowired
    private ExpertConsultationRepository consultationRepository;

    public ExpertConsultation submitQuery(ExpertConsultation consultation) {
        consultation.setRequestedAt(LocalDateTime.now());
        consultation.setStatus("PENDING");
        return consultationRepository.save(consultation);
    }

    public List<ExpertConsultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public Optional<ExpertConsultation> getById(String id) {
        return consultationRepository.findById(id);
    }

    public List<ExpertConsultation> getByFarmer(String farmerId) {
        return consultationRepository.findByFarmerId(farmerId);
    }

    public List<ExpertConsultation> getPendingConsultations() {
        return consultationRepository.findByStatus("PENDING");
    }

    public ExpertConsultation respondToQuery(String id, String response, String expertName) {
        return consultationRepository.findById(id).map(c -> {
            c.setResponse(response);
            c.setExpertName(expertName);
            c.setStatus("RESPONDED");
            c.setRespondedAt(LocalDateTime.now());
            return consultationRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Consultation not found"));
    }

    public ExpertConsultation updateConsultation(String id, ExpertConsultation updated) {
        return consultationRepository.findById(id).map(c -> {
            c.setSubject(updated.getSubject());
            c.setQuery(updated.getQuery());
            c.setPriority(updated.getPriority());
            c.setStatus(updated.getStatus());
            return consultationRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Consultation not found"));
    }

    public void deleteConsultation(String id) {
        consultationRepository.deleteById(id);
    }
}
