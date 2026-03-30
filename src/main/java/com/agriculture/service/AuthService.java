package com.agriculture.service;

import com.agriculture.config.JwtUtil;
import com.agriculture.model.Farmer;
import com.agriculture.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> register(Farmer farmer) {
        if (farmerRepository.existsByEmail(farmer.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
        Farmer saved = farmerRepository.save(farmer);
        String token = jwtUtil.generateToken(saved.getEmail());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("farmer", sanitize(saved));
        return result;
    }

    public Map<String, Object> login(String email, String password) {
        Optional<Farmer> farmerOpt = farmerRepository.findByEmail(email);
        if (farmerOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        Farmer farmer = farmerOpt.get();
        if (!passwordEncoder.matches(password, farmer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(farmer.getEmail());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("farmer", sanitize(farmer));
        return result;
    }

    private Farmer sanitize(Farmer farmer) {
        farmer.setPassword(null);
        return farmer;
    }
}
