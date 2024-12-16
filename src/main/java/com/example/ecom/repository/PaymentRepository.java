package com.example.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.entity.PaymentV2;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentV2, Long> {
    
}
