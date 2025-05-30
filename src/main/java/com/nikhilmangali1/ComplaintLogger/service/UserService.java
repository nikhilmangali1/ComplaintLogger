package com.nikhilmangali1.ComplaintLogger.service;

import com.nikhilmangali1.ComplaintLogger.dto.UserDTO;
import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inject BCryptPasswordEncoder



    public UserDTO mapToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRoles(user.getRoles());
        userDTO.setComplaintIds(user.getComplaintIds());
        return userDTO;
    }

    public List<UserDTO> mapToDtoList(List<User> users) {
        return users.stream().map(this::mapToDto).toList();
    }



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

    public User getByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public User updateUserById(String userId, User updateduser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        if (!existingUser.getUserName().equals(updateduser.getUserName()) &&
                userRepository.findByUserName(updateduser.getUserName()) != null) {
            throw new IllegalArgumentException("Username already taken");
        }


        // Update fields as necessary
        existingUser.setUserName(updateduser.getUserName());
        existingUser.setEmail(updateduser.getEmail());
        existingUser.setPhoneNumber(updateduser.getPhoneNumber());

        // If password is provided, encode it
        if (updateduser.getPassword() != null && !updateduser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateduser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}