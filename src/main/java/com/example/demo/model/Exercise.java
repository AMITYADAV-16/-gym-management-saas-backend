package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private String reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_day_id" ,nullable = false)
    private WorkoutDay workoutDay;
}
