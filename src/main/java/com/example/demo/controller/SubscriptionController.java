package com.example.demo.controller;

import com.example.demo.dto.PlanRequest;
import com.example.demo.model.Subscription;
import com.example.demo.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }
    @PostMapping("/plans")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Subscription> createPlan(@RequestParam Long gymId, @RequestBody PlanRequest request){
        return ResponseEntity.ok(subscriptionService.createPlane(gymId,request));
    }
    @GetMapping("/plans/{gymId}")
    public ResponseEntity<List<Subscription>> getPlans(@PathVariable Long gymId){
        return ResponseEntity.ok(subscriptionService.getPlansForGym(gymId));
    }
}
