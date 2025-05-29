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
    private PasswordEncoder passwordEncoder;

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
}
