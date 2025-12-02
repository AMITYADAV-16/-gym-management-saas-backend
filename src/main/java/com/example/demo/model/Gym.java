package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gyms")
@JsonIgnoreProperties({"hibernateLazyInitializer" , "handler"})
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(precision =  10 , scale = 7)
    private BigDecimal latitude;

    @Column(precision =  10 , scale = 7)
    private BigDecimal longitude;

@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="owner_id", nullable = false)
    private User owner;

@Column(length =10)
    private String phonenumber;

@Column(length = 100)
    private String contactEmail;
@Column(columnDefinition = "TEXT")
    private String description;

@OneToMany(mappedBy = "gym" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GymPhoto> photos;

@OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gym_facilities" ,
            joinColumns = @JoinColumn(name = "gym_id"),
    inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<Facility> facilities;
}
