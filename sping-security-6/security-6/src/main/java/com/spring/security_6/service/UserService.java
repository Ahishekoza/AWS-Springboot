package com.spring.security_6.service;

import com.spring.security_6.model.User;
import com.spring.security_6.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRespository userRespository;

    public User registerUser(User user){
//        -- encode password
        return userRespository.save(user);
    }

    public User getByUsername(String username){
        User existingUser = userRespository.findByUsername(username);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        return existingUser;
    }



}
