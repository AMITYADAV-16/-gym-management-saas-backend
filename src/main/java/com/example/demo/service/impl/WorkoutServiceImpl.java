package com.example.demo.service.impl;

import com.example.demo.dto.WorkoutLogRequest;
import com.example.demo.dto.WorkoutSplitRequest;
import com.example.demo.model.Exercise;
import com.example.demo.model.User;
import com.example.demo.model.*;
import com.example.demo.model.WorkoutDay;
import com.example.demo.model.WorkoutSplit;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutDayRepository;
import com.example.demo.repository.WorkoutLogRepository;
import com.example.demo.repository.WorkoutSplitRepository;
import com.example.demo.service.WorkoutService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.example.demo.model.WorkoutLog;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutSplitRepository workoutSplitRepository;
    private final UserRepository userRepository;
    private final WorkoutDayRepository workoutDayRepository;
    private final WorkoutLogRepository workoutLogRepository;

    public WorkoutServiceImpl(WorkoutSplitRepository workoutSplitRepository, UserRepository userRepository, WorkoutDayRepository workoutDayRepository, WorkoutLogRepository workoutLogRepository) {
        this.workoutSplitRepository = workoutSplitRepository;
        this.userRepository = userRepository;
        this.workoutDayRepository = workoutDayRepository;
        this.workoutLogRepository = workoutLogRepository;

    }

    @Override
    public List<WorkoutSplit> getWorkoutSplitsForUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return workoutSplitRepository.findByUserId(user.getId());
    }

    @Override
    @Transactional
    public WorkoutSplit createWorkoutSplit(WorkoutSplitRequest request, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutSplit split = new WorkoutSplit();
        split.setName(request.getName());
        split.setUser(user);

        split.setWorkoutDays(request.getDays().stream().map(dayRequest -> {
            WorkoutDay day = new WorkoutDay();
            day.setDayname(dayRequest.getDayName());
            day.setSplit(split);

            day.setExercises(dayRequest.getExercises().stream().map(exerciseRequest -> {
                Exercise exercise = new Exercise();
                exercise.setName(exerciseRequest.getName());
                exercise.setSets(exerciseRequest.getSets());
                exercise.setReps(exerciseRequest.getReps());
                exercise.setWorkoutDay(day); // Link the exercise back to the day
                return exercise;
            }).collect(Collectors.toList()));

            return day;
        }).collect(Collectors.toList()));

        // 4. Save the split. Cascade will save all days and exercises.
        return workoutSplitRepository.save(split);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutSplit> getWorkoutSplitById(Long id, String username) {
        Optional<WorkoutSplit> splitOptional = workoutSplitRepository.findById(id);

        if (splitOptional.isEmpty()) {
            return Optional.empty();
        }

        WorkoutSplit split = splitOptional.get();

        // Security Check: Make sure the split belongs to the user requesting it.
        if (!split.getUser().getEmail().equals(username)) {
            return Optional.empty();
        }

        return splitOptional;
    }

   @Override
    @Transactional
    public WorkoutLog logworkout(WorkoutLogRequest request, String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
      WorkoutDay workoutDay = workoutDayRepository.findById(request.getWorkoutDayId())
              .orElseThrow(() -> new RuntimeException("Workout not found"));

    if (workoutLogRepository.findByUser_IdAndDateCompleted(user.getId(), LocalDate.now()).isPresent()) {
        throw new IllegalStateException("A workout has already been logged for today.");
    }

        WorkoutLog newLog = new WorkoutLog();
        newLog.setUser(user);
        newLog.setWorkoutDay(workoutDay);
        newLog.setDateCompleted(LocalDate.now());
        newLog.setAttaindence(true);
        return workoutLogRepository.save(newLog);
    }
    @Override
    public List<WorkoutLog> getWorkoutLogsForUser(String username, LocalDate date) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // This allows fetching logs for a specific day, or all logs if no date is provided
        if (date != null) {
            return workoutLogRepository.findAllByUser_IdAndDateCompleted(user.getId(), date);
        } else {
            return workoutLogRepository.findAllByUser_Id(user.getId());
        }
    }
}