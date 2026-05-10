package com.spring.security_6.repository;


import com.spring.security_6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
