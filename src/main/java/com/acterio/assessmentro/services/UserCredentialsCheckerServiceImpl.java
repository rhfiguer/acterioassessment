package com.acterio.assessmentro.services;

import com.acterio.assessmentro.dtos.UserDTO;
import com.acterio.assessmentro.models.User;
import com.acterio.assessmentro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserCredentialsCheckerServiceImpl implements UserCredentialsCheckerService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<String> checkProblemsInCredentials(UserDTO userDTO) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(userDTO.getEmail()));
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User with this email does not exist.");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password.");
        }
        return null;
    }
}
