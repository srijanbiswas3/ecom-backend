package com.example.ecom.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryWrapper {
    
    private final PaymentRepository paymentRepository;
}
