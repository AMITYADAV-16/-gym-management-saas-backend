package com.example.demo.service;

import com.example.demo.dto.WorkoutLogRequest;
import com.example.demo.dto.WorkoutSplitRequest;
import com.example.demo.model.WorkoutDay;
import com.example.demo.model.WorkoutLog;
import com.example.demo.model.WorkoutSplit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    WorkoutSplit createWorkoutSplit(WorkoutSplitRequest request, String username);
    List<WorkoutSplit> getWorkoutSplitsForUser(String username);
    Optional<WorkoutSplit> getWorkoutSplitById(Long id, String username);
WorkoutLog logworkout(WorkoutLogRequest request , String username);

List<WorkoutLog> getWorkoutLogsForUser(String username, LocalDate date);
}
