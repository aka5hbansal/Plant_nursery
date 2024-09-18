package com.example.plantnursery.controller;

import com.example.plantnursery.model.Address;
import com.example.plantnursery.model.Customer;
import com.example.plantnursery.model.User;
import com.example.plantnursery.repository.CustomerRepository;
import com.example.plantnursery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addCustomerDetails(@PathVariable Long userId, @RequestBody Customer customerDetails) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        customerDetails.setUser(user.get());

        customerRepository.save(customerDetails);
        return ResponseEntity.ok("Customer details added successfully");
    }

    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<List<Address>> getCustomerAddresses(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        return ResponseEntity.ok(customer.getAddresses());
    }
}

