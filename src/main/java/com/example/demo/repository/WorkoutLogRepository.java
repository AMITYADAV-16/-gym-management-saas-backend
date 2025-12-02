package com.example.demo.repository;

import com.example.demo.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    Optional<WorkoutLog> findByUser_IdAndDateCompleted(Long userId, LocalDate dateCompleted);

    List<WorkoutLog> findAllByUser_Id(Long userId);

    List<WorkoutLog> findAllByUser_IdAndDateCompleted(Long userId, LocalDate dateCompleted);
}
