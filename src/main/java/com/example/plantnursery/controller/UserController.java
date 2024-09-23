package com.example.plantnursery.controller;


import com.example.plantnursery.DTOs.UserDTO;
import com.example.plantnursery.model.AuthenticationResponse;
import com.example.plantnursery.model.User;
import com.example.plantnursery.repository.UserRepository;
import com.example.plantnursery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }
}

