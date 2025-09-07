package com.project.backend.controllers.dto;

import com.project.backend.models.Role;

public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Role role; // optional; defaults to CUSTOMER

    // getters/setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
