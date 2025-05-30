package com.nikhilmangali1.ComplaintLogger.controller;

import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.model.enums.Role;
import com.nikhilmangali1.ComplaintLogger.security.JwtUtil;
import com.nikhilmangali1.ComplaintLogger.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<?> getUserWithComplaints(@PathVariable String userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userService.getByUsername(currentUsername);

        if (currentUser.getRoles().contains(Role.ADMIN) || currentUser.getId().equals(userId)) {
            return ResponseEntity.ok(userService.getUserWithComplaints(userId));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }


}
