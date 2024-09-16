package com.example.plantnursery.repository;

import com.example.plantnursery.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}