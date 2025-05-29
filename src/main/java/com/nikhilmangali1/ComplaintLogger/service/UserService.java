package com.nikhilmangali1.ComplaintLogger.service;

import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.repository.ComplaintRepository;
import com.nikhilmangali1.ComplaintLogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ComplaintRepository complaintRepository;

    public boolean findUser(User user) {
        if(user.getUserName() == null || user.getPassword() == null) {
            return false;
        }
        return userRepository.findByUserName(user.getUserName()) != null &&
               userRepository.findByUserName(user.getUserName()).getPassword().equals(user.getPassword());
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean loginUser(User user){
        String userName = user.getUserName();
        String password = user.getPassword();
        User existingUser = userRepository.findByUserName(userName);
        if (existingUser != null) {
            return existingUser.getPassword().equals(password);
        }
        return false;
    }


    public User getUserWithComplaints(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch complaints linked to the user
        List<Complaint> complaints = complaintRepository.findByUserId(userId);

        List<String> complaintIds = complaints.stream()
                .map(Complaint::getId)
                .collect(Collectors.toList());

        user.setComplaintIds(complaintIds);

        return user;
    }
}
