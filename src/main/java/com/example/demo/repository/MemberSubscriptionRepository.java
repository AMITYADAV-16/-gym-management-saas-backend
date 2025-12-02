package com.example.demo.repository;


import com.example.demo.model.MemberSubcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MemberSubscriptionRepository extends JpaRepository<MemberSubcription, Long> {
    Optional<MemberSubcription> findByUser_IdAndStatus(Long userId, com.example.demo.model.SubscriptionStatus status);

}
