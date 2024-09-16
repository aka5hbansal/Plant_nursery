package com.example.plantnursery.controller;

import com.example.plantnursery.model.Address;
import com.example.plantnursery.model.Customer;
import com.example.plantnursery.repository.AddressRepository;
import com.example.plantnursery.repository.CustomerRepository;
import com.example.plantnursery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return ResponseEntity.ok(addresses);
    }
}
