package com.example.demo.service;

import com.example.demo.dto.GymRequest;
import com.example.demo.model.Gym;

import java.util.List;

public interface GymService {

    Gym createGym(GymRequest gymRequest) throws Exception;
    List<Gym> getAllGyms();
}