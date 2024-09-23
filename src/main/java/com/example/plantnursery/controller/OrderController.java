package com.example.plantnursery.controller;

import com.example.plantnursery.DTOs.DeliveryRequest;
import com.example.plantnursery.model.Customer;
import com.example.plantnursery.model.Orders;
import com.example.plantnursery.service.OrderService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/customers/orders/{orderId}")
    public Orders getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/api/customers/orders/{orderId}/delivery")
    public ResponseEntity<String> selectDeliveryMethod(
            @PathVariable Long orderId,
            @RequestBody DeliveryRequest deliveryRequest) {

        try {
            orderService.updateDeliveryDetails(orderId, deliveryRequest);
            return ResponseEntity.ok("Delivery details updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/api/staff/orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> ordersList = orderService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }

}

