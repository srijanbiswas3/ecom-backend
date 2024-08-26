package com.example.ecom.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.dto.InitiatePaymentRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/ecom/payment")
@RequiredArgsConstructor
public class PaymentController {
    
    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody InitiatePaymentRequest request) {

        
        return null;
    }
}
