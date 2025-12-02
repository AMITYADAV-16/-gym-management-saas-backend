package com.example.demo.repository;

import com.example.demo.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {

    Optional<Diet> findByUser_IdAndDate(Long userId, LocalDate date);
}