package com.example.plantnursery.controller;

import com.example.plantnursery.DTOs.PaymentDetails;
import com.example.plantnursery.DTOs.PaymentRequest;
import com.example.plantnursery.DTOs.PaymentResponse;
import com.example.plantnursery.model.Orders;
import com.example.plantnursery.model.Payment;

import com.example.plantnursery.repository.OrderRepository;
import com.example.plantnursery.service.OrderService;
import com.example.plantnursery.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.plantnursery.model.Payment.PaymentMethod.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/payments")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
// Find the order by ID
        Orders order = orderService.findById(paymentRequest.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

// Create a new payment object
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(BigDecimal.valueOf(order.getTotalAmount()));
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        PaymentDetails details = paymentRequest.getPaymentDetails();

// Handle different payment methods
        switch (paymentRequest.getPaymentMethod()) {
            case CARD:
                if (details == null || details.getCardNumber() == null || details.getExpiryDate() == null || details.getCvv() == null) {
                    throw new IllegalArgumentException("Card details are incomplete");
                }
                payment.setPaymentDetails(details);
                payment.setPaymentStatus(Payment.PaymentStatus.PAID);
                order.setPaymentStatus(Payment.PaymentStatus.PAID); // Update order's payment status
                break;

            case UPI:
                if (details == null || details.getUpiId() == null) {
                    throw new IllegalArgumentException("UPI ID is missing");
                }
                PaymentDetails upiDetails = new PaymentDetails();
                upiDetails.setUpiId(details.getUpiId());
                payment.setPaymentDetails(upiDetails);
                payment.setPaymentStatus(Payment.PaymentStatus.PAID);
                order.setPaymentStatus(Payment.PaymentStatus.PAID); // Update order's payment status
                break;

            case COD:
                payment.setPaymentDetails(null); // No payment details needed for COD
                payment.setPaymentStatus(Payment.PaymentStatus.PENDING); // Pending until delivery
                order.setPaymentStatus(Payment.PaymentStatus.PENDING); // Order is pending payment
                break;

            default:
                throw new IllegalArgumentException("Unsupported payment method");
        }

// Set payment time to now
        payment.setPaymentTime(LocalDateTime.now());

// Save the payment and update the order's status
        Payment savedPayment = paymentService.save(payment);
        orderRepository.save(order); // Persist the updated order status

        // Return the response with payment details
        return new PaymentResponse(savedPayment.getId(), savedPayment.getPaymentStatus(), savedPayment.getPaymentTime());
    }
}