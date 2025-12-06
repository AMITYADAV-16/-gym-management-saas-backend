package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final PaymentRepository paymentRepo;
    private final SubscriptionRepository subRepo;
    private final UserRepository userRepo;
    private final MemberSubscriptionRepository memberSubRepo;

    public RazorpayService(PaymentRepository paymentRepo,
                           SubscriptionRepository subRepo,
                           UserRepository userRepo,
                           MemberSubscriptionRepository memberSubRepo) {
        this.paymentRepo = paymentRepo;
        this.subRepo = subRepo;
        this.userRepo = userRepo;
        this.memberSubRepo = memberSubRepo;
    }

    public Payment createOrder(Long planId, String userEmail) throws Exception {
        Subscription plan = subRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fakeOrderId = "order_test_" + System.currentTimeMillis();

        Payment payment = new Payment();
        payment.setRazorpayOrderId(fakeOrderId); // Unique ID
        payment.setAmount(plan.getPrice().doubleValue());
        payment.setUser(user);
        payment.setSubscription(plan);
        payment.setStatus("CREATED");
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepo.save(payment);
    }

    @Transactional
    public boolean verifyPayment(String orderId, String paymentId, String signature) throws Exception {
        Payment payment = paymentRepo.findByRazorpayOrderId(orderId);

        if (payment == null) {
            throw new RuntimeException("Order not found");
        }

        boolean isValid = true;

        if (isValid) {
            payment.setRazorpayPaymentId(paymentId);
            payment.setRazorpaySignature(signature);
            payment.setStatus("PAID");
            paymentRepo.save(payment);

            activateMembership(payment.getUser(), payment.getSubscription());
            return true;
        } else {
            payment.setStatus("FAILED");
            paymentRepo.save(payment);
            return false;
        }
    }

    private void activateMembership(User user, Subscription plan) {
        MemberSubcription sub = new MemberSubcription();
        sub.setUser(user);
        sub.setGym(plan.getGym());
        sub.setSubscription(plan);
        sub.setStartDate(LocalDate.now());
        sub.setEndDate(LocalDate.now().plusDays(plan.getDurationInDays()));
        sub.setStatus(SubscriptionStatus.ACTIVE);
        memberSubRepo.save(sub);
    }
}