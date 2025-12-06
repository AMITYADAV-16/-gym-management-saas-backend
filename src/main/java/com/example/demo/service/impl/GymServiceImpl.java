package com.example.demo.service.impl;

import com.example.demo.dto.GymRequest;
import com.example.demo.model.Gym;
import com.example.demo.model.User;
import com.example.demo.repository.GymRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GymService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;
    private final UserRepository userRepository;

    public GymServiceImpl(GymRepository gymRepository, UserRepository userRepository) {
        this.gymRepository = gymRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Gym createGym(GymRequest gymRequest) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User owner = userRepository.findByEmail(username)
                .orElseThrow(() -> new Exception("Authenticated user not found in database."));

        BigDecimal placeholderLat = new BigDecimal("34.0522");
        BigDecimal placeholderLon = new BigDecimal("-118.2437");

        Gym newGym = new Gym();
        newGym.setName(gymRequest.getName());
        newGym.setAddress(gymRequest.getAddress());
        newGym.setCity(gymRequest.getCity());
        newGym.setOwner(owner);
        newGym.setLatitude(placeholderLat);
        newGym.setLongitude(placeholderLon);

        return gymRepository.save(newGym);
    }
    @Override
    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }
}