package com.example.plantnursery.service;

import com.example.plantnursery.DTOs.UserDTO;
import com.example.plantnursery.model.AuthenticationResponse;
import com.example.plantnursery.model.User;
import com.example.plantnursery.repository.UserRepository;
import com.example.plantnursery.service.JwtService;
import com.example.plantnursery.model.Token;
import com.example.plantnursery.repository.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    public AuthenticationResponse register(UserDTO request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null, "User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.CUSTOMER); // Default role

        User savedUser = repository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(accessToken, refreshToken, savedUser);

        return new AuthenticationResponse(accessToken, refreshToken, "User registration was successful");
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    /*public UserDTO getProfile() {
        // Step 1: Get the currently authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // This will fetch the username from the JWT token

        // Step 2: Fetch the user by username from the database
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and return UserDTO
        UserDTO userProfileDTO = new UserDTO();
        userProfileDTO.setUsername(user.getUsername());
        userProfileDTO.setRole(user.getRole().name()); // Convert Role enum to string

        return userProfileDTO;
    }*/

    public List<User> getAllNonAdminUsers() {
        return repository.findAll()
                .stream()
                .filter(user -> user.getRole() != User.Role.STAFF)
                .collect(Collectors.toList());
    }

   /* public UserDTO updateUser(UserDTO updatedUserDTO) {
        // Get the current logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user details
        currentUser.setUsername(updatedUserDTO.getUsername()); // Assuming username can be updated
        currentUser.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword())); // Update password
        currentUser.setRole(User.Role.valueOf(updatedUserDTO.getRole())); // Set role

        // Save the updated user
        User updatedUser = repository.save(currentUser);

        // Return updated user details as DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(updatedUser.getUsername());
        userDTO.setRole(updatedUser.getRole().name()); // Convert Role enum to string

        return userDTO;
    }*/
}


