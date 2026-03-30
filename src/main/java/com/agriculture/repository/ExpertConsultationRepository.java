package com.agriculture.repository;

import com.agriculture.model.ExpertConsultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertConsultationRepository extends MongoRepository<ExpertConsultation, String> {
    List<ExpertConsultation> findByFarmerId(String farmerId);
    List<ExpertConsultation> findByStatus(String status);
    List<ExpertConsultation> findByPriority(String priority);
}
