package com.acterio.assessmentro.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // New column for role
    @Column(nullable = false)
    private String role;

    public User() {
    }

    public User(String email, String hashedPassword, String role) {
        this.email = email;
        this.password = hashedPassword;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }
}
