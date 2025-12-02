package com.example.demo.controller;

import com.example.demo.dto.DietRequest;
import com.example.demo.model.Diet;
import com.example.demo.service.DietService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/diets")
public class DietController {
    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @PostMapping
    public ResponseEntity<Diet> saveDiet(@RequestBody DietRequest request, Authentication auth) {
        return ResponseEntity.ok(dietService.saveOrUpdateDiet(request, auth.getName()));
    }

    @GetMapping
    public ResponseEntity<Diet> getDiet(@RequestParam(required = false) LocalDate date, Authentication auth) {
        if (date == null) {
            date = LocalDate.now();
        }
        return ResponseEntity.ok(dietService.getDietByDate(date, auth.getName()));

    }
}