package com.example.plantnursery.DTOs;

import com.example.plantnursery.model.Payment;

public class PaymentRequest {
    private Long orderId;
    private Payment.PaymentMethod paymentMethod;
    private PaymentDetails paymentDetails; // Change to PaymentDetails object

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Payment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}