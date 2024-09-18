package com.example.plantnursery.service;

import com.example.plantnursery.model.Payment;
import com.example.plantnursery.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment findById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.orElse(null); // or throw an exception if preferred
    }

}
