package com.nikhilmangali1.ComplaintLogger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/home")
    public String welcomeHome(){
        return "Welcome";
    }
}
