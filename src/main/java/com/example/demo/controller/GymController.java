package com.example.demo.controller;


import com.example.demo.dto.GymRequest;
import com.example.demo.model.Gym;
import com.example.demo.service.GymService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gyms")
public class GymController {

    private final GymService gymService;

    public GymController(GymService gymService) {

        this.gymService = gymService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
public ResponseEntity<Gym> createGym(@RequestBody GymRequest gymRequest){
        try {
            Gym createdGym = gymService.createGym(gymRequest);
            return new ResponseEntity<>(createdGym, HttpStatus.CREATED);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<List<Gym>> getAllGyms() {
        return ResponseEntity.ok(gymService.getAllGyms());
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<String> testSecurity() {

        return ResponseEntity.ok("You have access!");
    }
}
