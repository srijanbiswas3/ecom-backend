package com.example.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecom.entity.User;

public interface UsersRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);
    
}
