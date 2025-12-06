package com.example.demo.service;

import com.example.demo.dto.PlanRequest;
import com.example.demo.model.Gym;
import com.example.demo.repository.GymRepository;
import com.example.demo.model.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
private final SubscriptionRepository subscriptionRepo;
private final GymRepository gymRepo;

public SubscriptionService(SubscriptionRepository subscriptionRepo,GymRepository gymRepo){
    this.subscriptionRepo = subscriptionRepo;
    this.gymRepo = gymRepo;
}
@Autowired
private UserRepository userRepository;
public Subscription createPlane (Long gymId,PlanRequest request){
    Gym gym = gymRepo.findById(gymId)
            .orElseThrow(() -> new RuntimeException("Gym not found"));
String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

if(!gym.getOwner().getEmail().equals(currentUsername)){
    throw new RuntimeException("ACCESS DENIED: You do not own this gym!");
}
    Subscription plan = new Subscription();
plan.setGym(gym);
plan.setPlanName(request.getName());
plan.setPrice(request.getPrice());
plan.setDurationInDays(request.getDuration());
return subscriptionRepo.save(plan);
}
public List<Subscription> getPlansForGym(Long gymId){
    return subscriptionRepo.findByGymId(gymId);
}
}
