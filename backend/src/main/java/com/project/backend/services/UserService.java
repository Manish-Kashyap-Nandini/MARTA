package com.project.backend.services;

import com.project.backend.models.Role;
import com.project.backend.models.User;
import com.project.backend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) { 
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        if (user.getRole() == null) user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) { 
        return userRepository.findByEmail(email); 
    }
}
