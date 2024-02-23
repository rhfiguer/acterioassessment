package com.acterio.assessmentro.services;

import com.acterio.assessmentro.dtos.UserDTO;
import com.acterio.assessmentro.models.User;
import com.acterio.assessmentro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserRegistrationService {
    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    public UserRegistrationService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<String> registerNewUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with email " + userDTO.getEmail() + " already exists.");
        }

        User user = new User(userDTO.getEmail(), userDTO.getPassword(), "USER_ROLE");
        userService.registerUser(user);
        return null;
    }
}
