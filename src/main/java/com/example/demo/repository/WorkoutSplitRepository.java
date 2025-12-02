package com.example.demo.repository;

import com.example.demo.model.WorkoutSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkoutSplitRepository extends JpaRepository<WorkoutSplit, Long> {
List<WorkoutSplit> findByUserId(Long userId);
}

