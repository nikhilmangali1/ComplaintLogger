package com.nikhilmangali1.ComplaintLogger.controller;

import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userService.findUser(user)) {
            return "User already exists";
        }
        userService.saveUser(user);
        return "User registered successfully";
    }


    @PostMapping("/login")
    public String login(@RequestBody User user) {
        boolean authenticated = userService.findUser(user);
        if (authenticated) {
            return "Login successful";
        }
        return "Invalid username or password";
    }
}
