package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "diets")
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;
    private LocalDate date;
    @Column(columnDefinition = "TEXT")
    private String breakfast;
    @Column(columnDefinition = "TEXT")
    private String lunch;
    @Column(columnDefinition = "TEXT")
    private String dinner;
    @Column(columnDefinition = "TEXT")
    private String snacks;

}
