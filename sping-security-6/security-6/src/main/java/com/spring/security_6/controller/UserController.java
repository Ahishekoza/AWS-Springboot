package com.spring.security_6.controller;

import com.spring.security_6.model.User;
import com.spring.security_6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
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
    public String loginUser(@RequestBody User user){
        return  userService.verifyLoggedInUser(user);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        if(authentication.isAuthenticated()){
            String username = authentication.getName();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return ResponseEntity.ok(Map.of(
                    "username",username,
                    "authorities",userDetails.getAuthorities()
            ));
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

}
