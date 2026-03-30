package com.agriculture.repository;

import com.agriculture.model.Farmer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerRepository extends MongoRepository<Farmer, String> {
    Optional<Farmer> findByEmail(String email);
    boolean existsByEmail(String email);
}
