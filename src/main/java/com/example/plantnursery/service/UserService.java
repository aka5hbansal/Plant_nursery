package com.example.plantnursery.service;

import com.example.plantnursery.model.User;
import com.example.plantnursery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        userRepository.save(user);
        return "User registered successfully";
    }
}


