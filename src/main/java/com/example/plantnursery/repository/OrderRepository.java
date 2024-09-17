package com.example.plantnursery.repository;

import com.example.plantnursery.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}

