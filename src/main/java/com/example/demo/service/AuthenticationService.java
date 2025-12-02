package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        // FIX 1: Use correct Enum names (ROLE_OWNER / ROLE_MEMBER)
        Role userRole;
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("OWNER")) {
            userRole = Role.ROLE_OWNER;
        } else {
            userRole = Role.ROLE_MEMBER;
        }

        var user = new User();
        // FIX 2: Use correct camelCase for setters (setFirstName)
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);

        repository.save(user);

        // FIX 3: Pass email string, not User object (if that's what your JwtService expects)
        var jwtToken = jwtService.generateToken(user.getEmail());

        // FIX 4: Use the constructor correctly
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        // FIX 3: Pass email string
        var jwtToken = jwtService.generateToken(user.getEmail());

        // FIX 4: Use constructor
        return new AuthResponse(jwtToken);
    }
}