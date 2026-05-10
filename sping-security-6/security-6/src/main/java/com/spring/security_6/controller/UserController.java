package com.spring.security_6.controller;

import com.spring.security_6.model.User;
import com.spring.security_6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUserRegister(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public boolean loginUser(@RequestBody User user){
        User loggedUser = userService.getByUsername(user.getUsername());
        if(Objects.isNull(loggedUser)){
            return false;
        }
        return true;

    }
}
