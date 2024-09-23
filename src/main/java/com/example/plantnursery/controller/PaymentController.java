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
@RequestMapping
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/api/customers/payments")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {

        Orders order = orderService.findById(paymentRequest.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(BigDecimal.valueOf(order.getTotalAmount()));
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        PaymentDetails details = paymentRequest.getPaymentDetails();

        switch (paymentRequest.getPaymentMethod()) {
            case CARD:
                if (details == null || details.getCardNumber() == null || details.getExpiryDate() == null || details.getCvv() == null) {
                    throw new IllegalArgumentException("Card details are incomplete");
                }
                payment.setPaymentDetails(details);
                payment.setPaymentStatus(Payment.PaymentStatus.PAID);
                order.setPaymentStatus(Payment.PaymentStatus.PAID);
                break;

            case UPI:
                if (details == null || details.getUpiId() == null) {
                    throw new IllegalArgumentException("UPI ID is missing");
                }
                PaymentDetails upiDetails = new PaymentDetails();
                upiDetails.setUpiId(details.getUpiId());
                payment.setPaymentDetails(upiDetails);
                payment.setPaymentStatus(Payment.PaymentStatus.PAID);
                order.setPaymentStatus(Payment.PaymentStatus.PAID);
                break;

            case COD:
                payment.setPaymentDetails(null);
                payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
                order.setPaymentStatus(Payment.PaymentStatus.PENDING);
                break;

            default:
                throw new IllegalArgumentException("Unsupported payment method");
        }

        payment.setPaymentTime(LocalDateTime.now());

        Payment savedPayment = paymentService.save(payment);
        orderRepository.save(order);

        return new PaymentResponse(savedPayment.getId(), savedPayment.getPaymentStatus(), savedPayment.getPaymentTime());
    }
}