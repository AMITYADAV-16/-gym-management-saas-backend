package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final UserRepository userRepo;

    public TrainerController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @PutMapping("/promote/{userId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> promoteToTrainer(@PathVariable Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.ROlE_TRAINER);
        userRepo.save(user);

        return ResponseEntity.ok("User " + user.getFirstName() + " is now a Trainer!");
    }
}