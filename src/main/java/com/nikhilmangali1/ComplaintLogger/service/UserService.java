package com.nikhilmangali1.ComplaintLogger.service;

import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inject BCryptPasswordEncoder

    public boolean userExists(String username) {
        return userRepository.findByUserName(username) != null;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUserName(username);
        if (user == null) return false;
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User getUserWithComplaints(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}