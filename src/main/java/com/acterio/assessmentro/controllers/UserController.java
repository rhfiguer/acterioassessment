package com.acterio.assessmentro.controllers;

import com.acterio.assessmentro.dtos.UserDTO;
import com.acterio.assessmentro.models.User;
import com.acterio.assessmentro.repositories.UserRepository;
import com.acterio.assessmentro.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailDomainService domainService;
    private final UserCredentialsCheckerServiceImpl credentialsChecker;
    private final UserRegistrationService userRegistrationService;
    private final UserDeletionService userDeletionService;


    @Autowired
    public UserController(UserService userService, UserCredentialsCheckerServiceImpl credentialsChecker, UserRegistrationService userRegistrationService, UserDeletionService userDeletionService) {
        this.userService = userService;
        this.credentialsChecker = credentialsChecker;
        this.userRegistrationService = userRegistrationService;
        this.userDeletionService = userDeletionService;
    }

    @GetMapping("/home")
    public String home() {
        return "Hello, you're testing HOME now";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/domain")
    public ResponseEntity<Map<String, Long>> getDomainCounts() {
        Map<String, Long> domainCounts = domainService.countDomainTypes();
        return ResponseEntity.ok(domainCounts);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseEntity<String> registrationResponse = userRegistrationService.registerNewUser(userDTO);
            if (registrationResponse != null) return registrationResponse;

            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try {
            ResponseEntity<String> authenticationResponse = credentialsChecker.checkProblemsInCredentials(userDTO);
            if (authenticationResponse != null) return authenticationResponse;

            // A JWT token should be generated here...
            return ResponseEntity.ok("Hi, I owe you a JWT here...!");

        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error im during login.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email) {
        try {
            ResponseEntity<String> deletionResponse = userDeletionService.deleteUserByEmail(email);
            if (deletionResponse != null) return deletionResponse;

            return ResponseEntity.ok("User with email '" + email + "' deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User deletion by email failed: " + e.getMessage());
        }
    }
}

