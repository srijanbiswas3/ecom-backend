package com.example.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.entity.Order;
import com.example.ecom.exception.UserNotFoundException;
import com.example.ecom.service.OrderService;
import com.example.ecom.util.JwtUtils;

@RestController
// @CrossOrigin(origins = {"http://localhost:5173","https://ecom-java-backend-ad4067ce2405.herokuapp.com"}, allowedHeaders = "*", allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    JwtUtils jwtUtils;

    
    @GetMapping("/orders")
    public List<Order> getUserOrders(@RequestHeader("Authorization") String accessToken) {
        // Extract the access token from the request header
        // Perform any necessary validation/authentication
        boolean verified = jwtUtils.verifyToken(accessToken);
    
        // Check if token verification failed
        if (!verified) {
            // Token verification failed, return an error response or handle as needed
            // For example, you can throw an exception or return an empty list of orders
            throw new UserNotFoundException("Token verification failed");
        }
    
        // Token verification succeeded, call the service method to retrieve user orders
        return orderService.getUserOrders(accessToken);
    }
    
}
