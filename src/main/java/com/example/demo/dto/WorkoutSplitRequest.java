package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkoutSplitRequest {
private String name;
private List<WorkoutDayRequest> days;
}

