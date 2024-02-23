package com.acterio.assessmentro.services;

import com.acterio.assessmentro.models.User;
import com.acterio.assessmentro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserDeletionService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with email '" + email + "' not found.");
        }

        userRepository.delete(user);
        return ResponseEntity.ok("User with email '" + email + "' deleted successfully!");
    }
}
