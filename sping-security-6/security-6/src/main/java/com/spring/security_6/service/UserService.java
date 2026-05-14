package com.spring.security_6.service;

import com.spring.security_6.model.User;
import com.spring.security_6.repository.UserRespository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRespository userRespository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    // Constructor Injection (Best Practice)
    public UserService(UserRespository userRespository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRespository = userRespository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRespository.save(user);
    }

    public User getByUsername(String username) {
        User existingUser = userRespository.findByUsername(username);
        if (existingUser == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return existingUser;
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    public String verifyLoggedInUser(User user) {
       Authentication authenticationManager1 =  authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
       );

       if (authenticationManager1.isAuthenticated()){
           return jwtService.generateToken(user);
       }
       return "Invalid Token or Credentials";

    }



}
