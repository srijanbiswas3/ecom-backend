package com.example.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.entity.Order;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.service.OrderService;
import com.example.ecom.util.JwtUtils;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public List<Order> getUserOrders(String accessToken) {
        // Implement your authentication logic to validate the access token and retrieve
        // the user ID
        Long userId = (long)jwtUtils.extractClaims(accessToken).get("userId");

        // Use the retrieved user ID to fetch the user's orders
        return orderRepository.findByUserId(userId);
    }

}
