package com.example.plantnursery.service;

import com.example.plantnursery.model.Customer;
import com.example.plantnursery.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Method to find a customer by ID
    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null); // Use Optional to safely handle absence
    }

    // Method to get all customers
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    // Method to save a customer
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    // Method to delete a customer by ID
    public void deleteById(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}

