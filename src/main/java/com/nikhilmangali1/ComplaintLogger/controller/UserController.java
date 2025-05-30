package com.nikhilmangali1.ComplaintLogger.controller;

import com.nikhilmangali1.ComplaintLogger.dto.UserDTO;
import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.model.enums.Role;
import com.nikhilmangali1.ComplaintLogger.security.JwtUtil;
import com.nikhilmangali1.ComplaintLogger.service.ComplaintService;
import com.nikhilmangali1.ComplaintLogger.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userService.userExists(user.getUserName())) {
            return "User already exists";
        }
        userService.saveUser(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        boolean authenticated = userService.authenticate(user.getUserName(), user.getPassword());
        if (authenticated) {

            User existingUser = userService.getByUsername(user.getUserName());
            Set<Role> roles = existingUser.getRoles();

            String jwt = jwtUtil.generateToken(user.getUserName(),roles);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }



    @Getter
    public static class JwtResponse {
        private final String jwt;
        public JwtResponse(String jwt) {
            this.jwt = jwt;
        }
    }

    @GetMapping("/{userId}/withComplaints")
    public ResponseEntity<?> getUserComplaints(@PathVariable String userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userService.getByUsername(currentUsername);

        if (currentUser.getRoles().contains(Role.ADMIN) || currentUser.getId().equals(userId)) {
            User targetUser = userService.getUserWithComplaints(userId);
            if (targetUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            // Fetch complaints based on complaintIds
            List<Complaint> complaints = complaintService.getComplaintsByIds(targetUser.getComplaintIds());
            return ResponseEntity.ok(complaints);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }




    @PutMapping("/updateUser/{userId}")
    @PreAuthorize("hasRole('USER') or #userId == authentication.principal.username")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable String userId, @RequestBody User updatedUser) {
        User updated = userService.updateUserById(userId, updatedUser);
        return ResponseEntity.ok(userService.mapToDto(updated));
    }


    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId) {
        User user = userService.getUserWithComplaints(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(userService.mapToDto(user));
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = userService.mapToDtoList(users);
        return ResponseEntity.ok(userDTOs);
    }




}
