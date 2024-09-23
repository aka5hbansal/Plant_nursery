package com.example.plantnursery.repository;

import com.example.plantnursery.model.Customer;
import com.example.plantnursery.model.Orders;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}

