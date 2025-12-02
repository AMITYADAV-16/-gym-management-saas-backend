package com.example.demo.controller;

import com.example.demo.model.Payment;
import com.example.demo.service.RazorpayService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final RazorpayService razorpayService;

    public PaymentController(RazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    @PostMapping("/create-order/{planId}")
    public ResponseEntity<Payment> createOrder(@PathVariable Long planId, Authentication auth) throws Exception {
        return ResponseEntity.ok(razorpayService.createOrder(planId, auth.getName()));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        try {
            boolean isSuccess = razorpayService.verifyPayment(
                    request.getRazorpayOrderId(),
                    request.getRazorpayPaymentId(),
                    request.getRazorpaySignature()
            );
            return isSuccess ? ResponseEntity.ok("Payment Verified & Membership Active")
                    : ResponseEntity.badRequest().body("Signature Verification Failed");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}

@Data
class PaymentVerificationRequest {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}