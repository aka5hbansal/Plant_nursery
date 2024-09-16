package com.example.plantnursery.repository;

import com.example.plantnursery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

