package com.example.demo.repository;

import com.example.demo.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    @Query("SELECT g FROM Gym g WHERE g.latitude BETWEEN :minLat AND :maxLat AND g.longitude BETWEEN :minLon AND :maxLon")
    List<Gym> findGymsInBoundingBox(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLon") double minLon,
            @Param("maxLon") double maxLon
    );
    List<Gym> findByOwnerId(Long ownerId);

}
