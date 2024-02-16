package com.example.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecom.entity.Jwt;
import com.example.ecom.entity.User;
import java.util.List;


public interface JwtRepository extends JpaRepository<Jwt, Long> {

    Jwt findByUser(User user);

    List<Jwt> findByRefreshToken(String refreshToken);

    
}
