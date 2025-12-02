package com.example.demo.repository;

import com.example.demo.model.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Long> {
}
