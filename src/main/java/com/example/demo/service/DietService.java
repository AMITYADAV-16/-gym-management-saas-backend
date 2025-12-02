package com.example.demo.service;

import com.example.demo.dto.DietRequest;
import com.example.demo.model.Diet;
import com.example.demo.model.User;
import com.example.demo.repository.DietRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DietService {

    private final DietRepository dietRepository;
    private final UserRepository userRepository;

    public DietService(DietRepository dietRepository, UserRepository userRepository) {
        this.dietRepository = dietRepository;
        this.userRepository = userRepository;
    }

    public Diet saveOrUpdateDiet(DietRequest request, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<Diet> existingDiet = dietRepository.findByUser_IdAndDate(user.getId(), request.getDate());

        Diet diet;
        if (existingDiet.isPresent()) {

            diet = existingDiet.get();
        } else {

            diet = new Diet();
            diet.setUser(user);
            diet.setDate(request.getDate());
        }

        if (request.getBreakfast() != null) diet.setBreakfast(request.getBreakfast());
        if (request.getLunch() != null) diet.setLunch(request.getLunch());
        if (request.getDinner() != null) diet.setDinner(request.getDinner());
        if (request.getSnacks() != null) diet.setSnacks(request.getSnacks());

        return dietRepository.save(diet);
    }

    public Diet getDietByDate(LocalDate date, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return dietRepository.findByUser_IdAndDate(user.getId(), date)
                .orElse(null);
    }
}