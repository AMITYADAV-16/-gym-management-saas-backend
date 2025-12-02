package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkoutDayRequest {
private String dayName;
private List<ExerciseRequest> exercises;
}
