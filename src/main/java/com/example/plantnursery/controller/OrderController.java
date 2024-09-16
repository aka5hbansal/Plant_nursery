package com.example.plantnursery.controller;

import com.example.plantnursery.DTOs.OrderRequest;
import com.example.plantnursery.model.Orders;
import com.example.plantnursery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequest orderRequest) {
        Orders orders = orderService.createOrder(orderRequest.getCustomerId(), orderRequest.getItems(), orderRequest.getDeliveryMethod());
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }
}

