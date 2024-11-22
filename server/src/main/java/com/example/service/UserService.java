package com.example.service;

import com.example.model.Membership;
import com.example.model.User;
import org.springframework.stereotype.Service;
import com.example.repository.UserRepository;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Add password hashing in production
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.authenticate(password)) // Matches hashed password
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
    }

    public User updateUserProfile(String username, List<String> memberships) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("com.example.model.User not found"));
        user.getMemberships().clear();
        for (String membershipName : memberships) {
            Membership membership = new Membership();
            membership.setName(membershipName);
            user.addMembership(membership);
        }
        return userRepository.save(user);
    }

}

