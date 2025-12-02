package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

    @Data
    @Entity
    @Table(name = "gym_photos")
    public class GymPhoto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String photoUrl; // URL to the uploaded image

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "gym_id", nullable = false)
@JsonIgnore
        private Gym gym;
    }
