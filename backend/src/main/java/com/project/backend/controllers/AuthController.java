package com.project.backend.controllers;

import com.project.backend.controllers.dto.AuthRequest;
import com.project.backend.controllers.dto.SignupRequest;
import com.project.backend.models.Role;
import com.project.backend.models.User;
import com.project.backend.services.UserService;
import com.project.backend.utils.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder; // still needed for login
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Signup
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest req) {
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword()); // ❌ no encoding here
        u.setRole(req.getRole() == null ? Role.CUSTOMER : req.getRole());

        userService.registerUser(u); // service handles encoding

        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully!",
                "email", u.getEmail(),
                "role", u.getRole().name()
        ));
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 🔑 Generate token using email
        String token = jwtUtil.generateToken(loginRequest.getEmail());

        // 🎯 Fetch role for frontend routing
        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", user.getRole().name()
        ));
    }
}
