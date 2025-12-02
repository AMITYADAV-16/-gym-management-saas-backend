package com.example.demo.controller;

import com.example.demo.dto.WorkoutLogRequest;
import com.example.demo.dto.WorkoutSplitRequest;
import com.example.demo.model.WorkoutLog;
import com.example.demo.model.WorkoutSplit;
import com.example.demo.service.WorkoutService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

public WorkoutController(WorkoutService workoutService){
    this.workoutService=workoutService;
}

@PostMapping("/splits")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<WorkoutSplit> createWorkoutSplit(@RequestBody WorkoutSplitRequest request, Authentication authentication){
    String username = authentication.getName();

    WorkoutSplit createdSplit = workoutService.createWorkoutSplit(request, username);

    return new ResponseEntity<>(createdSplit, HttpStatus.CREATED);
}
    @GetMapping("/splits")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WorkoutSplit>> getWorkoutSplits(Authentication authentication) {
        String username = authentication.getName();
        List<WorkoutSplit> splits = workoutService.getWorkoutSplitsForUser(username);
        return ResponseEntity.ok(splits);
    }

    // (Optional) Add this for fetching a specific split by ID
    @GetMapping("/splits/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WorkoutSplit> getWorkoutSplitById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return workoutService.getWorkoutSplitById(id, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/logs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logworkout(@RequestBody WorkoutLogRequest request , Authentication authentication){
    try{
        String username =authentication.getName();
    WorkoutLog createdLog = workoutService.logworkout(request, username);
    return new ResponseEntity<>(createdLog, HttpStatus.CREATED);

    }catch (IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    }

    @GetMapping("/logs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WorkoutLog>> getWorkoutLogs(
            Authentication authentication,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date){
    String username = authentication.getName();
    List<WorkoutLog> logs = workoutService.getWorkoutLogsForUser(username, date);
    return ResponseEntity.ok(logs);
    }

}
