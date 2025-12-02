package com.example.demo.repository;

import com.example.demo.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // This method is required by RazorpayService to find the payment by Order ID
    Payment findByRazorpayOrderId(String razorpayOrderId);

}