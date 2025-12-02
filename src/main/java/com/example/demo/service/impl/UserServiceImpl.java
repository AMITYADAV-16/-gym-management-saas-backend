package com.example.demo.service.impl;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User userFromRequest) throws Exception {
        // 1. Check if email exists
        Optional<User> existingUser = userRepository.findByEmail(userFromRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("Email is already registered.");
        }

        // 2. Create new User object and copy fields
        User newUser = new User();
        newUser.setEmail(userFromRequest.getEmail());
        newUser.setFirstName(userFromRequest.getFirstName());
        newUser.setLastName(userFromRequest.getLastName());

        // 3. Encrypt Password
        newUser.setPassword(passwordEncoder.encode(userFromRequest.getPassword()));

        // 4. ROLE LOGIC (The Fix)
        // Check what role the user ASKED for
        Role requestedRole = userFromRequest.getRole();

        // Only allow them to be OWNER or MEMBER.
        // If they ask to be TRAINER (or null), force them to be MEMBER.
        if (requestedRole == Role.ROLE_OWNER) {
            newUser.setRole(Role.ROLE_OWNER);
        } else {
            // Default to MEMBER for everyone else (Safety check)
            newUser.setRole(Role.ROLE_MEMBER);
        }

        return userRepository.save(newUser);
    }
}