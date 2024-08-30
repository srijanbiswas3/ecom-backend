package com.example.ecom.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ecom.entity.Order;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.PaymentRepositoryWrapper;
import com.example.ecom.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    
    @Value("${payments.base.url}")
    private String baseUrl;

    @Value("${payments.initiate.url}")
    private String initateEndpoint;

    private final PaymentRepositoryWrapper paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public String initiatePayment(final Long orderId) {
        return null;
    }
}
